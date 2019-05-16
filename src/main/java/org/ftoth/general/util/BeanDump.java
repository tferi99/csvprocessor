package org.ftoth.general.util;

import java.beans.BeanInfo;
import java.beans.Expression;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Iterator;

public class BeanDump
{
	// internal exceptions
	public static class NotInitedHibernateCollection extends Exception {
		private static final long serialVersionUID = -2227635545151537772L;
		
	};
	
	public static class UnknownPropertyError extends Exception
	{
		private static final long serialVersionUID = 7051186339241453670L;

		UnknownPropertyError() {
			super();
		}
		
		UnknownPropertyError(Throwable e) {
			super(e);
		}
	};
	
	public static String dump(Object o)
	{
		return dump(o, null, null, (String[])null);
	}

	public static String dump(Object o, String title)
	{
		return dump(o, null, title, (String[])null);
	}

	public static String dump(Object o, String title, String ownPackageName)
	{		
		return dump(o, null, title, new String[]{ownPackageName});
	}

	public static String dump(Object o, String title, String[] ownPackageNames)
	{		
		return dump(o, null, title, ownPackageNames);
	}
	
	public static String dump(Object o, String[] excludeProps, String title, String ownPackageName)
	{
		return dump(o, excludeProps, title, new String[]{ownPackageName});
	}
	
	@SuppressWarnings("unchecked")
	public static String dump(Object o, String[] excludeProps, String title, String[] ownPackageNames)
	{
		StringBuffer b = new StringBuffer();
		
		if (title != null) {
			b.append("=================== " + title + " =========================\n");
		}
		if (o == null) {
			b.append("<null>");
		}
		else {
			if (o instanceof Collection) {					// it's a collection
				b.append(dumpCollection("", (Collection)o, excludeProps, "", ownPackageNames));
			}
			if (o instanceof Object[]) {					// it's an array
				b.append(dumpArray("", (Object[])o, excludeProps, "", ownPackageNames));
			}
			else {
				b.append(dumpObject(o, excludeProps, "", ownPackageNames));
			}
		}
		return b.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String dumpObject(Object o, String[] excludeProps, String offset, String[] ownPackageNames)
	{		
		StringBuffer b = new StringBuffer();
		String offsetHere = offset + "  ";
		
		try {
			b.append(offset + "DUMP [" + o.toString() + "]\n");

	        BeanInfo bi = Introspector.getBeanInfo(o.getClass());
	        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
	        for (int i=0; i<pds.length; i++) {
	            // Get property name
	            String propName = pds[i].getName();
	            if (excludeProps != null) {
	            	if (isInArrayIgnoreCase(excludeProps, propName)) {
	            		b.append(offsetHere + "- " + propName + "=[excluded]\n");
	            		continue;
	            	}
	            }
	            Object prop = null;
	            
	            try {
	            	prop = getPropValue(o, propName);
	            }
	            catch (NotInitedHibernateCollection e) {
	            	b.append(offsetHere + "- " + propName + " = [not initialized Hibernate collection]\n");
	            }
	            catch (UnknownPropertyError e) {
	            	String orig = "";
	            	if (e.getCause() != null) {
	            		orig = "- " + e.getCause().getMessage(); 
	            	}
	            	b.append(offsetHere + "- " + propName + " = [error] " + orig + "\n");
	            }
	            
				if (prop instanceof Collection) {					// it's a collection
					b.append(offsetHere + "- " + propName + "[" + prop.getClass().getName() + "]:\n");
					b.append(dumpCollection(propName, (Collection)prop, excludeProps, offsetHere, ownPackageNames));
				}
				else if (prop instanceof Object[]) {					// it's an array
					b.append(offsetHere + "- " + propName + "[" + prop.getClass().getName() + "]:\n");
					b.append(dumpArray(propName, (Object[])prop, excludeProps, offsetHere, ownPackageNames));
				}
				else { // it's NOT a collection
					if (prop == null) {
						b.append(offsetHere +"- " + propName + " = [null]\n");						
					}
					else {
						// non-Hibernate
						b.append(offsetHere +"- " + propName + "[" + prop.getClass().getName() + "] = " + prop.toString() + "\n");
						if (ownPackageNames != null) {
							for (String packageName : ownPackageNames) {
								int len = packageName.length();
								if (getSubString(prop.getClass().getName(), len).equals(packageName)) {
									b.append(dumpObject(prop, excludeProps, offsetHere + "  ", ownPackageNames));		// extra 2 spaces
									break;
								}
							}
						}
						
						// Hibernate
/*						try {
							b.append(offsetHere +"- " + propName + "[" + prop.getClass().getName() + "] = " + prop.toString() + "\n");
						}
						catch (LazyInitializationException e) {
							b.append(offsetHere +"- " + propName + "[" + prop.getClass().getName() + "] = " + e.getMessage() + "\n");
						}*/
					}
				}
	        }
	    } catch (Exception e) {
	    	b.append(offsetHere + "Error during object dump [" + e.getMessage() + "]\n");
	    }	    
	    return b.toString();
	}

	private static String dumpArray(String propName, Object o[], String[] excludeProps, String offset, String[] ownPackageNames)
	{
		StringBuffer b = new StringBuffer();
		String offsetHere = offset;
		
		b.append(offsetHere + ">>>>>>>>>>>>> " + propName + "[array] >>>>>>>>>>>>>\n");
		for (int i = 0; i < o.length; i++) {
			Object element = o[i];
			b.append(dumpObject(element, excludeProps, offsetHere, ownPackageNames));
		}
		b.append(offsetHere + "<<<<<<<<<<<<< " + propName + "[array] <<<<<<<<<<<<<\n");
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	public static String dumpCollection(String propName, Collection coll, String[] excludeProps, String offset, String[] ownPackageNames)
	{
		StringBuffer b = new StringBuffer();
		String offsetHere = offset;
		
		b.append(offsetHere + ">>>>>>>>>>>>> " + propName + "[collection] >>>>>>>>>>>>>\n");
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			Object element = (Object)iter.next();
			b.append(dumpObject(element, excludeProps, offsetHere, ownPackageNames));
		}
		b.append(offsetHere + "<<<<<<<<<<<<< " + propName + "[collection] <<<<<<<<<<<<<\n");
		return b.toString();
	}
	
	
	
	public static Object getPropValue(Object o, String propName) throws UnknownPropertyError, NotInitedHibernateCollection
	{
		String getter = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
		Expression expr = new Expression(o, getter, new Object[0]);
		Object val = null;
		try {
			expr.execute();
			val = expr.getValue();
		}
		catch (NoSuchMethodException e) {
			// try to call again with 'is'
			getter = "is" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
			expr = new Expression(o, getter, new Object[0]);
			val = null;
			try {
				expr.execute();
				val = expr.getValue();
			}
			catch (Exception e2) {
				throw new UnknownPropertyError(e2);
			}				
		}
		catch (Exception e) {
			throw new UnknownPropertyError(e);
		}				

		if (val == null) {
			return null;
		}
		// Hibernate
/*		if (val instanceof PersistentSet) {
			if (!((PersistentSet)val).wasInitialized()) {
				throw new NotInitedHibernateCollection();
			}
		}*/
		return val;
	}

	private static boolean isInArrayIgnoreCase(String[] arr, String s)
	{
		for (int n=0; n<arr.length; n++) {
			if (s.equalsIgnoreCase(arr[n])) {
				return true;
			}
		}
		return false;
	}

	// ----------------- helpers -----------------
	private static String getSubString(String s, int len)
	{
		if (s == null || s.length() < len) {
			return s;
		}
		return s.substring(0, len);
	}
}
