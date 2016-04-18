package com.tbjfund.frameword.tpa.maven;

import com.tbjfund.framework.tpa.TemplateBuilder;
import com.tbjfund.framework.tpa.config.TableConfig;
import com.tbjfund.framework.tpa.scan.ClassPathScanner;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * @xxgoal tpa
 */
@Mojo(name = "tpa", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = false)
public class Plugin extends AbstractMojo {

    @Parameter(defaultValue = "${project.compileSourceRoots}", readonly = true, required = true)
    private List<String> compileSourceRoots;

    @Parameter(defaultValue = "${project.compileClasspathElements}", readonly = true, required = true)
    private List<String> classpathElements;

    @Parameter(defaultValue = "${project.build.outputDirectory}", required = true, readonly = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project.artifact}", readonly = true, required = true)
    private Artifact projectArtifact;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/annotations")
    private File generatedSourcesDirectory;

    @Parameter(property = "maven.main.skip")
    private boolean skipMain;

    private String scanPackage = "com.tbjfund";

    private String printRoot;

    private XmlFormat xmlFormat = new XmlFormat();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info(" ------------------------------------------------------------------------");
        getLog().info(" 开始处理 @Table @Column 注解");

        ClassLoader befor = Thread.currentThread().getContextClassLoader();
        try {
            printRoot = getPrintRoot();
            getLog().info(" 输出目录: " + printRoot);
            getLog().info(" 类路径:" + classpathElements);
            getLog().info(" ------------------------------------------------------------------------");

            URL[] urls = new URL[classpathElements.size()];

            for (int i = 0; i < classpathElements.size(); i++) {
                String path = classpathElements.get(i);
                try {
                    File f = new File(path);
                    urls[i] = (f.toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            SpringClassLoder classLoader = new SpringClassLoder(urls, Thread.currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(classLoader);

            ClassPathScanner scan = new ClassPathScanner();

            List<TableConfig> tables = scan.scan(scanPackage);

            print(tables);

            getLog().info("[INFO] ------------------------------------------------------------------------");

        } catch (Exception e) {
            getLog().error(e);
        }finally {
            Thread.currentThread().setContextClassLoader(befor);
        }
    }

    /**
     * 获取输出目录
     * @return
     */
    private String getPrintRoot(){
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        String path = null;
        getLog().info(" 操作系统:" + os);
        if (os.toUpperCase().indexOf("WIN") != -1){
            path = "c:" + File.separator;
        }else {
            path = "/tmp/tpa/";
        }
        return path;
    }

    /**
     * 输出
     * @param tables
     */
    private void print(List<TableConfig> tables){
        if (tables == null || tables.size() == 0){
            return;
        }
        File root = new File(printRoot);
        if (!root.exists()){
            root.mkdirs();
        }

        for (TableConfig table : tables){
            try {
                printMapper(table);
                getLog().info("[INFO] @Table :" + table.getNamespace());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMapper(TableConfig table) throws IOException {
        String buffer = TemplateBuilder.build(table, TemplateBuilder.MAPPER);
        printFile(buffer, table.getNamespace(), table.getTableName() + ".xml");

        buffer = TemplateBuilder.build(table, TemplateBuilder.SERVICE_INTERFACE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Service.java");

        buffer = TemplateBuilder.build(table, TemplateBuilder.SERVICE_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "ServiceImpl.java");

        buffer = TemplateBuilder.build(table, TemplateBuilder.DAO_INTERFACE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Dao.java");

        buffer = TemplateBuilder.build(table, TemplateBuilder.DAO_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "DaoImpl.java");

        buffer = TemplateBuilder.build(table, TemplateBuilder.DUBBO_FACADE);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "Facade.java");

        buffer = TemplateBuilder.build(table, TemplateBuilder.DUBBO_IMPL);
        printFile(buffer, table.getNamespace(), table.getBeanName() + "FacadeImpl.java");
    }

    private void printFile(String buffer, String pkg,  String name) throws IOException {
        File packageFile = new File(printRoot + pkg);
        if (!packageFile.exists()){
            packageFile.mkdir();
        }
        File outFile = new File(printRoot + pkg + File.separator + name);
        if (outFile.exists()){
            outFile.delete();
        }
        FileWriter writer = new FileWriter(outFile);
        writer.write(buffer);
        writer.close();
    }

}
