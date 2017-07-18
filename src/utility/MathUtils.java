package utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;



public class MathUtils {

	public static List<?> getRandomSublist(final List<?> list, final int totalSamples){
	
		Collections.shuffle(list);
		return list.subList(0, totalSamples);
	}
	
	public static synchronized String printDescriptiveStatistic(ConcurrentLinkedDeque<Double> objs){
		
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(Double obj: objs){
			stats.addValue(obj);
		}
		DecimalFormat df = new DecimalFormat( "0.0" );
		
		StringBuilder sb = new StringBuilder();
		sb.append("#Samples\tAverage\tMedian\t90%Line\t95%Line\t99%Line\tMin\tMax\t\n");
		sb.append(stats.getN()).append("\t").append(df.format(stats.getMean())).append("\t").append(df.format(stats.getPercentile(50)))
				.append("\t").append(df.format(stats.getPercentile(90))).append("\t").append(df.format(stats.getPercentile(95))).append("\t")
				.append(df.format(stats.getPercentile(99))).append("\t").append(df.format(stats.getMin())).append("\t").append(df.format(stats.getMax()))
				.append("\t\n");
		
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	private static Random rand = new Random();
	
	public static <T extends Object> T getRandomValue(final List<T> objs){
		
		return objs.get(rand.nextInt(objs.size()));
	}
	
	/**
	 * 
	 * @param minInclusive:included
	 * @param maxInclusive: excluded
	 * @return
	 */
	public static int getRandomValue(final int minInclusive, final int maxExclusive){
		
		return minInclusive + (int)(Math.random() * (maxExclusive-minInclusive));
	}
	
	public static double getRandomValue(final double minInclusive, final double maxExclusive){
		
		return minInclusive + (Math.random() * (maxExclusive - minInclusive));
	}
	
	public static boolean isNotLessThan(double a, double b){
		
		return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).signum()>=0;
	}
	
	public static boolean isLargerThan(double a, double b){
		
		return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).signum() > 0;
	}
	
	public static boolean isWithinRange(double a, double b){
		
		return MathUtils.isWithinRange(a, b, Constant.DELTA_VALUES);
	}
	/**True if |a-b| <= range
	 * 
	 * @param a
	 * @param b
	 * @param range
	 * @return
	 */
	public static boolean isWithinRange(double a, double b, double range){
		
		return (BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b))).abs().subtract(BigDecimal.valueOf(range)).signum() <= 0;
	}
	
	public static double round(double a, int scale){
		
		return BigDecimal.valueOf(a).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double extractDouble(final String str){
		
		double result = 0.0;
		
		if(!Checker.isBlank(str)){
			String temp = str;
	 		
			if(temp.contains(",")){
				temp = temp.replaceAll(",", "");//","->"." not work for SG
			}
			if(temp.contains("%")){
				temp = temp.replaceAll("%", "");
			}
			try{
				result = Double.parseDouble(temp);
			}catch(Exception e){
				Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]+.[0-9]+");
				Matcher m = p.matcher(temp);
				if(m.find()){
					String strNum = m.group();
					result = Double.parseDouble(strNum);
				}	
			}	
		}
		
		return result;
	}
	
	public static double calculateIRR(double loanAmount, double loanTenure, double monthlyRepayment){
		
		double[] valueArray = new double[(int)loanTenure];
		for(int i = 0; i < valueArray.length; i ++){
			valueArray[i] = monthlyRepayment;
		}
		
		return calculateIRR(loanAmount, loanTenure, valueArray);
	}
	
	public static double calculateIRR(double loanAmount, double loanTenure, double[] monthlyRepayments){
		
		double[] valueArray = new double[monthlyRepayments.length + 1];
		valueArray[0] = - loanAmount;
		for(int i =  0; i < monthlyRepayments.length; i ++){
			valueArray[i + 1] = monthlyRepayments[i]; 
		}
		
		return irr(valueArray);
	}
	
	public static double irr(double[] values) {

		int maxIterationCount = Math.max(20, values.length * 2);
        double absoluteAccuracy = 1E-7;
        double guess = 0.1d;
        double x1 = 0.0;

        int i = 0;
        while (i < maxIterationCount) {

            // the value of the function (NPV) and its derivate can be calculated in the same loop
            double fValue = 0;
            double fDerivative = 0;
            for (int k = 0; k < values.length; k++) {
                fValue += values[k] / Math.pow(1.0 + guess, k);
                fDerivative += -k * values[k] / Math.pow(1.0 + guess, k + 1);
            }

            // the essense of the Newton-Raphson Method
            x1 = guess - fValue / fDerivative;

            if (Math.abs(x1 - guess) <= absoluteAccuracy) {
                break;
            }

            guess = x1;
            ++i;
        }
        
        return Math.abs(x1);
    }
	
	public static double calculateRate(double loanAmount, double loanTenure, double monthlyRepayment) {
        /*
         * Instead of iterating until a guess is found, we have decided to only iterate a max of 20 or double the tenure
         * which should be enough to find the correct value
         */
        int maxIterationCount = Math.max(20, (int)loanTenure * 2);
        final double absoluteAccuracy = 1E-7;
        double high = 1.00;
        double low = 0.00;

        double rate = (2.0 * (loanTenure * monthlyRepayment - loanAmount)) / (loanAmount * loanTenure);

        int i = 0;
        while (i < maxIterationCount) {
            // check for error margin
            double calc = Math.pow(1 + rate, loanTenure);
            calc = (rate * calc) / (calc - 1.0);
            calc -= monthlyRepayment / loanAmount;

            if (calc > absoluteAccuracy) {
                // guess too high, lower the guess
                high = rate;
                rate = (high + low) / 2;
            } else if (calc < -absoluteAccuracy) {
                // guess too low, higher the guess
                low = rate;
                rate = (high + low) / 2;
            } else {
                // acceptable guess
                break;
            }
            ++i;
        }

        return rate;
    }
}
