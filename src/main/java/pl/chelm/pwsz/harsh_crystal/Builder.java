package pl.chelm.pwsz.harsh_crystal;

import java.beans.PropertyDescriptor;

public abstract class Builder<T> {
	public <V> Builder<T> setProperty(String key, final V value) {
		System.out.println(String.format("Trying to set property \"%s\" of class \"%s\" to \"%s\" of type \"%s\".", key, this.getClass(), value, value.getClass()));
		try {
			(new PropertyDescriptor(key, this.getClass()))
			.getWriteMethod()
			.invoke(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public <V> V getProperty(final String key) {
		System.out.println(String.format("Trying to read property \"%s\" of class \"%s\".", key, this.getClass()));
		try {
			return (V)(new PropertyDescriptor(key, this.getClass()))
			.getReadMethod()
			.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract T build();
}
