package nc.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

public class ReflectionHelper {
	
	public static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; ++i) {
			classes[i] = objects[i].getClass();
		}
		return classes;
	}
	
	/** NOTE: The constructor parameter types must match the argument types EXACTLY - they can NOT be superclasses */
	public static <T> T newInstance(Class<T> clazz, Object... args) {
		Constructor<T> constructor;
		try {
			constructor = clazz.getConstructor(getClasses(args));
			return constructor.newInstance(args);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}

	public static class CloneClassVisitor extends ClassVisitor {

		public final String cloneName;

		public CloneClassVisitor(ClassVisitor cv, String cloneName) {
			super(Opcodes.ASM5, cv);
			this.cloneName = cloneName;
		}

		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			super.visit(version, access, cloneName, signature, superName, interfaces);
		}
	}

	public static class DynamicClassLoader extends ClassLoader {

		public Class<?> defineClass(String name, byte[] bytes) {
			return defineClass(name, bytes, 0, bytes.length);
		}
	}

	public static final DynamicClassLoader CLASS_LOADER = new DynamicClassLoader();

	@SuppressWarnings("unchecked")
	public static <T> Class<T> defineClass(String name, ClassWriter cw) {
		return (Class<T>) CLASS_LOADER.defineClass(name, cw.toByteArray());
	}
	
	public static <T> Class<T> cloneClass(Class<? extends T> clazz, String cloneName) {
		try {
			ClassReader cr = new ClassReader(clazz.getName());
			ClassWriter cw = new ClassWriter(cr, 0);

			String fullName = clazz.getPackage().getName() + "." + cloneName;
			cr.accept(new CloneClassVisitor(cw, fullName.replace(".", "/")), 0);

			return defineClass(fullName, cw);
		}
		catch (IOException e) {
			throw new UnsupportedOperationException();
		}
	}
}
