import java.io.File;
import java.io.IOException;

public class Converter {
    /**
     *
     * @param inputFile
     *            原图片路径
     * @param outputFile
     *            webp路径
     * @param quality
     *            图片质量1-100
     * @return
     */
    private static boolean executeCWebp(String inputFile, String outputFile, Integer quality) {
        boolean result = false;
        ClassLoader cl = Converter.class.getClassLoader();
        // 换成相应的路径
        String cwebpPath = cl.getResource("bin/cwebp.exe").getPath();
        //System.out.println("cwebpPath=" + cwebpPath);
        try {
            StringBuilder command = new StringBuilder(cwebpPath);
            command.append(" -q " + (quality == 0 ? 90 : quality));
            command.append(" " + inputFile);
            command.append(" -o " + outputFile);
            Runtime.getRuntime().exec(command.toString());
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isFilePathExist(File file) {
        // 判断文件是否存在
        if (file.exists()) {
            System.out.println(file + "\n文件已存在\n");
            return true;
        } else {
            return false;
        }
    }

    public static void getFile(File file) {

        if (file != null) {
            File[] f = file.listFiles();
            if (f != null) {
                for (int i = 0; i < f.length; i++) {
                    getFile(f[i]);
                }
            } else {
                String filepath=file.toString();
                String suffix = filepath.substring(filepath.lastIndexOf("."));
                String exsuffix=filepath.substring(0,filepath.lastIndexOf("."));
                if(suffix.equals(".jpg")||suffix.equals(".png")||suffix.equals(".jpeg")||suffix.equals(".gif"))
                {
                    if(!isFilePathExist(new File(exsuffix+".webp"))) {
                        System.out.println(file);
                        runconverter(file.toString(), exsuffix + ".webp");
                    }
                }

            }
        }
    }
    public static void runconverter(String inputFile,String outputFile){
        if (executeCWebp(inputFile, outputFile, 90)) {
            System.out.println("已创建webp~\n");
        } else {
            System.out.println("sth wrong happened");
        }
    }
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        String path = System.getProperty("user.dir")+"\\"+args[0];
        File f = new File(path);
        getFile(f);

    }
}