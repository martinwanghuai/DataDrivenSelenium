import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class SumTo100 {

	public static class Solution{
		private List<Choice> choices = null;
		
		public Solution(final List<Choice> choices){
			this.choices = choices;
		}
		
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("=");
			for(Choice choice: choices){
				sb.append(choice).append("");
			}
			return sb.toString();
		}
	}
	
	public static class Choice {
		private String operand;
		private String operator;
		
		public Choice(final String operand, final String operator){
			this.operand = operand;
			this.operator = operator;
		}

		@Override
		public String toString() {
			return operator.equals(" ")? operand:operator+"" + operand;
		}
	}
	
	private BigInteger evaluate(Solution solution){
		
		List<String> operands = solution.choices.stream().map(choice -> choice.operand).collect(Collectors.toList());
		List<String> operators = solution.choices.stream().map(choice -> choice.operator).collect(Collectors.toList());
		
		List<String> refinedOperands = Lists.newArrayList();
		List<String> refinedOperators = Lists.newArrayList();
		
		for(int i = 0; i < operators.size(); i ++){
			String operator = operators.get(i);
			String operand = operands.get(i);
			if(operator.equals(" ")){
				if(i == 0){
					refinedOperands.add(operand);
				}else{
					if(refinedOperands.size() == 0){
						refinedOperands.add(operand);
					}else{
						String num = refinedOperands.remove(refinedOperands.size() -1 );
						num = num + operand;
						refinedOperands.add(num);
					}
				}
			}else{
				refinedOperands.add(operand);
				refinedOperators.add(operator);
			}
		}

		BigInteger sum = null;
		if(refinedOperands.size() == refinedOperators.size()){
			if(refinedOperators.get(0).equals("+")){
				sum = new BigInteger(refinedOperands.get(0));
				
			}else if(refinedOperators.get(0).equals("-")){
				sum = new BigInteger("-" +refinedOperands.get(0));
			}	
			refinedOperators.remove(0);
		}else if(refinedOperands.size() - refinedOperators.size() == 1){
			sum = new BigInteger(refinedOperands.get(0)); 
		}
		for(int i = 0; i < refinedOperators.size(); i ++){
			if(refinedOperands.size() > i + 1){ 
				BigInteger operand = new BigInteger(refinedOperands.get(i + 1));
				String operator = refinedOperators.get(i);
				if(operator.equals("+")){
					sum = sum.add(operand);
				}else if(operator.equals("-")){
					sum = sum.subtract(operand) ;
				}		
			}
		}
		
		return sum;
	}

	
	public Solution constructSolution(List<String> numbers, List<String> operators){
		
		Assert.assertEquals(numbers.size(),  operators.size());
		List<Choice> choices = Lists.newArrayList();
		for(int i = 0; i < operators.size(); i ++){
			Choice choice = new Choice(numbers.get(i), operators.get(i));
			choices.add(choice);
		}
		
		return new Solution(choices);
	}
	
	public List<List<String>> constructPermutation(List<String> strs, int size){
		
		List<List<String>> results = Lists.newArrayList();
		if(size == 1){
			for(String str: strs){
				results.add(Lists.newArrayList(str));
			}
		}else{
			List<List<String>> subResults = this.constructPermutation(strs, size - 1);
			for(List<String> subResult: subResults){
				for(String str: strs){
					List<String> result = Lists.newArrayList(subResult);
					result.add(str);
					results.add(result);
				}
			}
		}
		
		return results;
	}

	
	public List<Solution> constructValidSolutions(List<String> numbers, List<List<String>> operators, BigInteger sum){
		
		List<Solution> solutions = Lists.newArrayList();
		for(List<String> operatorList: operators){
			Solution solution = this.constructSolution(numbers, operatorList);
			if(this.evaluate(solution).subtract(sum).intValue() == 0 ){
				solutions.add(solution);	
			}
		}
		
		return solutions;
	}
	
	public List<Solution> sumToNumber(List<String> numbers, List<String> operators, BigInteger sum){
		
		List<List<String>> operatorPermutation =  this.constructPermutation(operators, numbers.size());
		return this.constructValidSolutions(numbers, operatorPermutation, sum);
	}
	
	@Test
	public void testCase2(){
		
		List<String> numbers = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		List<String> operators = Lists.newArrayList(" ", "+", "-");
		BigInteger sum = new BigInteger("100");
		List<Solution> results = this.sumToNumber(numbers, operators, sum);
		StringBuilder sb = new StringBuilder();
		sb.append("Size:" + results.size() + "\n");
		for(Solution solution: results){
			sb.append(solution+"\n");
		}
		
		System.out.println(sb.toString());
	}
	
	
}
