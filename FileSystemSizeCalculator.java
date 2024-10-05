package com.restatrent;

import java.util.HashMap;
import java.util.Map;

public class FileSystemSizeCalculator {
    public static void main(String[] args) {
        Map<String, Object> fileSystem = createSampleFileSystem();

        String path1 = "C:\\Users\\Lenovo\\OneDrive\\Desktop\\root\\dir1\\subdir1";
        String path2 = "root.dir2.subdir4.subsubdir5";
        String path3 = "C:\\javapractise\\Day31";

        int size1 = calculateTotalSize(fileSystem, path1);
        int size2 = calculateTotalSize(fileSystem, path2);
        int size3 = calculateTotalSize(fileSystem, path3);

        System.out.println("Input 1: path = " + path1);
       
        System.out.println("Actual Output-1: Total size: " + size1);
        System.out.println();

        System.out.println("Input 2: path = " + path2);
       
        System.out.println("Actual Output-2: Total size: " + size2);
        System.out.println();
        System.out.println("Input 3: path = " + path3);
        
         System.out.println("Actual Output-3: Total size: " + size3);
         System.out.println();
    }

    private static int calculateTotalSize(Map<String, Object> fileSystem, String path) {
        String[] pathComponents = path.split("\\.");

        Map<String, Object> currentDirectory = fileSystem;
        for (int i = 1; i < pathComponents.length; i++) {
            if (currentDirectory.containsKey(pathComponents[i])) {
                Object value = currentDirectory.get(pathComponents[i]);
                if (value instanceof Map) {
                    currentDirectory = (Map<String, Object>) value;
                } else {
                    System.out.println("Directory not found: " + path);
                    return 0;
                }
            } else {
                System.out.println("Directory not found: " + path);
                return 0;
            }
        }

        int totalSize = 0;
        for (Object value : currentDirectory.values()) {
            if (value instanceof Integer) {
                totalSize += (int) value;
            } else if (value instanceof Map) {
                totalSize += calculateTotalSize((Map<String, Object>) value, "");
            }
        }
        return totalSize;
    }

    private static Map<String, Object> createSampleFileSystem() {
        Map<String, Object> fileSystem = new HashMap<>();
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> dir1 = new HashMap<>();
        Map<String, Object> subdir1 = new HashMap<>();
        Map<String, Object> dir2 = new HashMap<>();
        Map<String, Object> subdir4 = new HashMap<>();
        Map<String, Object> subsubdir5 = new HashMap<>();

        subdir1.put("file1", 100);
        subdir1.put("file2", 900);
        dir1.put("subdir1", subdir1);
        root.put("dir1", dir1);

        subsubdir5.put("file3", 1000);
        subsubdir5.put("file4", 2400);
        subdir4.put("subsubdir5", subsubdir5);
        dir2.put("subdir4", subdir4);
        root.put("dir2", dir2);

        fileSystem.put("root", root);
        return fileSystem;
    }
}
