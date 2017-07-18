package utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

public class ReflectionUtils {
	
	public static boolean peekClass(final String clzName){
		
		Class clz = ReflectionUtils.loadClass(clzName);
		return clz != null;
	}
	
	public static Class<?> loadClass(final String clzName) {
		
		Class<?> clz = null;
		try {
			clz = Class.forName(clzName);
		} catch (final ClassNotFoundException e) {
		}

		return clz;
	}
	
	public static boolean containField(Class<?> clz, final String fieldName) {
		
		boolean contain = false;
		
		do{
			final Field[] fields = clz.getDeclaredFields();
			
			for (final Field field : fields) {
				if (field.getName().equals(fieldName)) {
					contain = true;
					break;
				}
			}
			
			if(!contain){
				clz = clz.getSuperclass();
			}
		}while(clz != null && !contain);
		
		return contain;
	}

	public static boolean containMethod(Class<?> clz, final String methodName) {

		boolean contain = false;
		do{
			final Method[] methods = clz.getDeclaredMethods();
			for (final Method method : methods) {
				if (method.getName().equals(methodName)) {
					contain = true;
					break;
				}
			}	
			
			if(!contain){
				clz = clz.getSuperclass();
			}
		}while(clz != null && !contain);
		
		return contain;
	}

	public static Object invokeMethod(Class<?> clz, final String methodName, Object... args){

		Object result = null;
		try{
			Method m = null;
			boolean found = false;
			
			do{
				final Method[] methods = clz.getDeclaredMethods();
				for (final Method method : methods) {
					if (method.getName().equals(methodName)) {
						m = method;
						found = true;
						break;
					}
				}	
				
				if(!found){
					clz = clz.getSuperclass();
				}
			}while(clz != null && !found);
			
			if(found){
				result = m.invoke(clz, args);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Method getMethod(Class<?> clz, final String methodName){
		
		Method m = null;
		boolean found = false;
		
		do{
			final Method[] methods = clz.getDeclaredMethods();
			for (final Method method : methods) {
				if (method.getName().equals(methodName)) {
					m = method;
					found = true;
					break;
				}
			}	
			
			if(!found){
				clz = clz.getSuperclass();
			}
		}while(clz != null && !found);
		
		return m;
	}
	
	public static Object invokeConstructor(Class<?> clz, Class argType1, Object arg1, Class argType2, Object arg2){
		
		Object result = null;
		try {
			Constructor<?> cons = clz.getConstructor(argType1, argType2);
			result = cons.newInstance(arg1, arg2);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static Object invokeConstructor(Class<?> clz, Class argType, Object arg){
		
		Object result = null;
		try {
			Constructor<?> cons = clz.getConstructor(argType);
			result = cons.newInstance(arg);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Class<?>> getAllClassesInPackage(String packageName){
		
		List<Class<?>> clzList = Lists.newArrayList();
		try{
			final ClassLoader loader = Thread.currentThread().getContextClassLoader();

			for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
			  if (info.getName().startsWith(packageName)) {
			    final Class<?> clazz = info.load();
			    clzList.add(clazz);
			  }
			}	
		}catch(Exception e){
			
		}
		return clzList;
	}
	
	public static List<Class<?>> getAllSubClassesInPackage(String packageName, Class<?> clazz){
		
		List<Class<?>> clzList = getAllClassesInPackage(packageName);
		return clzList.stream().filter(clz -> clazz.isAssignableFrom(clz) && !clazz.getName().equalsIgnoreCase(clz.getName())).collect(Collectors.toList());
	}
}
