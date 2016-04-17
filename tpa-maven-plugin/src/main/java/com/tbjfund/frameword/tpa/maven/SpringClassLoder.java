package com.tbjfund.frameword.tpa.maven;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by sidawei on 16/4/17.
 */
public class SpringClassLoder extends URLClassLoader {

    public SpringClassLoder(URL[] urls, ClassLoader classLoader) {
        super(urls, classLoader);
    }

    public Class findClass(String clazz) throws ClassNotFoundException {
        return super.findClass(clazz);
    }

}
