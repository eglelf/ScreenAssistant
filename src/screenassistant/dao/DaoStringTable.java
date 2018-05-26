package screenassistant.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import screenassistant.model.StringTable;

/**
 *
 * @author egle_ferreira
 */
public class DaoStringTable {

    private String dir;

    public DaoStringTable(String dir) {
        this.dir = dir;
        saveCurrentPath(dir);
    }

    public DaoStringTable() {
        this.dir = getLastFile();
    }

    private void saveCurrentPath(String dir) {
        try {
            String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().toString();
            currentDir = (currentDir.startsWith("/")) ? currentDir.substring(1) : currentDir;
            String folders[] = currentDir.split("/");

            if (currentDir.endsWith("/")) {
                currentDir = currentDir + "ScreenAssistantConfig.txt";
            } else {
                currentDir = currentDir.replaceAll(folders[folders.length - 1], "ScreenAssistantConfig.txt");
            }

            File file = new File(currentDir);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("LastPath = " + dir);
            bw.close();
            fw.close();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getLastFile() {
        try {
            String currentDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().toString();
            currentDir = (currentDir.startsWith("/")) ? currentDir.substring(1) : currentDir;
            String folders[] = currentDir.split("/");

            if (currentDir.endsWith("/")) {
                currentDir = currentDir + "ScreenAssistantConfig.txt";
            } else {
                currentDir = currentDir.replaceAll(folders[folders.length - 1], "ScreenAssistantConfig.txt");
            }

            //System.out.println("currentDir:" + currentDir);
            File file = new File(currentDir);
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String linha = sc.nextLine();
                    if (linha.startsWith("LastPath")) {
                        String keyValue[] = linha.split("=");
                        if (keyValue.length > 1) {
                            String lastDir = keyValue[1].trim();
                            File fileLastDir = new File(lastDir);
                            if (fileLastDir.exists()) {
                                return lastDir;
                            }
                        }
                        break;
                    }
                }
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return "";
    }

    public List<StringTable> getStringTable() {

        if (!this.dir.isEmpty()) {
            try {
                Properties props = new Properties();
                FileInputStream file = new FileInputStream(dir);
                props.load(file);

                ArrayList<StringTable> data = new ArrayList<>();
                Set<Object> chaves = props.keySet();
                for (Object chave : chaves) {
                    String key = (String) chave;
                    if (key.trim().endsWith(".1")) {
                        String value = props.getProperty(key);
                        StringTable st = new StringTable();
                        st.setKey(key.replace(".1", ""));
                        st.setValue(value);
                        data.add(st);
                    }
                }

                return data;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return new ArrayList<>();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        saveCurrentPath(dir);
    }

}
