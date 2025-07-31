package com.wrath.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale; // 확장자 비교를 위해 Locale을 사용하는 것이 좋음

/**
 * 특정 디렉토리에서 이미지 파일 목록을 가져오는 유틸리티 클래스입니다.
 */
public class ImageFileLister {

    // 지원하는 이미지 파일 확장자 목록
    private static final String[] IMAGE_EXTENSIONS = {
            "jpg", "jpeg", "png", "gif", "bmp", "webp", "tiff"
    };

    /**
     * 지정된 디렉토리에서 이미지 파일 목록을 가져옵니다.
     *
     * @param directoryPath 이미지 파일을 검색할 디렉토리의 경로
     * @return 해당 디렉토리에 있는 이미지 파일들의 File 객체 리스트.
     *         디렉토리가 존재하지 않거나, 디렉토리가 아니거나, 파일을 읽을 권한이 없으면 빈 리스트를 반환합니다.
     */
    public static List<File> listImageFiles(String directoryPath) {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        // 1. 디렉토리가 존재하는지 확인
        if (!directory.exists()) {
            System.out.println("오류: 디렉토리가 존재하지 않습니다 -> " + directoryPath);
            return imageFiles; // 빈 리스트 반환
        }

        // 2. 입력된 경로가 실제로 디렉토리인지 확인
        if (!directory.isDirectory()) {
            System.out.println("오류: 지정된 경로가 디렉토리가 아닙니다 -> " + directoryPath);
            return imageFiles; // 빈 리스트 반환
        }

        // 3. 디렉토리 내의 파일들을 필터링하여 가져옵니다.
        // FilenameFilter를 사용하여 이미지 파일만 선택합니다.
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // 파일 이름이 null이 아니어야 하고
                if (name == null) {
                    return false;
                }
                // 소문자로 변환하여 확장자를 비교합니다.
                String lowerCaseName = name.toLowerCase(Locale.ROOT); // 일관된 소문자 변환
                for (String ext : IMAGE_EXTENSIONS) {
                    if (lowerCaseName.endsWith("." + ext)) {
                        return true;
                    }
                }
                return false;
            }
        });

        // 4. 필터링된 파일들을 리스트에 추가 (null 체크)
        if (files != null) {
            imageFiles.addAll(Arrays.asList(files));
        } else {
            System.out.println("경고: 디렉토리에서 파일을 가져올 수 없습니다 (권한 문제일 수 있음) -> " + directoryPath);
        }

        return imageFiles;
    }

    // 테스트를 위한 test 메서드
    public static void test() {
        // --- 테스트 디렉토리 생성 (예시용, 실제 사용시 필요 없음) ---
        String testDirName = "test_images";
        File testDir = new File(testDirName);
        if (!testDir.exists()) {
            testDir.mkdirs(); // 디렉토리가 없으면 생성
        }
        try {
            // 더미 이미지 파일 생성 (실제 내용은 없어도 됨)
            new File(testDir, "image1.jpg").createNewFile();
            new File(testDir, "photo.png").createNewFile();
            new File(testDir, "document.pdf").createNewFile(); // 이미지 아님
            new File(testDir, "picture.gif").createNewFile();
            new File(testDir, "test.txt").createNewFile(); // 이미지 아님
            new File(testDir, "thumbnail.webp").createNewFile();
            new File(testDir, "IMG_001.JPG").createNewFile(); // 대문자 확장자 테스트
            System.out.println("테스트 디렉토리 '" + testDirName + "' 및 더미 파일 생성 완료.");
        } catch (IOException e) {
            System.err.println("더미 파일 생성 중 오류 발생: " + e.getMessage());
        }
        // --------------------------------------------------------

        // 1. 현재 실행 경로에 있는 'test_images' 폴더에서 이미지 파일 목록 가져오기
        String targetDirectory = testDirName; // 위에서 생성한 테스트 디렉토리
        System.out.println("\n--- '" + targetDirectory + "' 폴더에서 이미지 파일 목록 검색 ---");
        List<File> imagesInTestDir = ImageFileLister.listImageFiles(targetDirectory);

        if (imagesInTestDir.isEmpty()) {
            System.out.println("'" + targetDirectory + "' 폴더에 이미지 파일이 없습니다.");
        } else {
            System.out.println("'" + targetDirectory + "' 폴더의 이미지 파일 목록:");
            for (File imageFile : imagesInTestDir) {
                System.out.println("  - " + imageFile.getName() + " (경로: " + imageFile.getAbsolutePath() + ")");
            }
        }

        // 2. 존재하지 않는 폴더 테스트
        String nonExistentDir = "non_existent_folder";
        System.out.println("\n--- '" + nonExistentDir + "' 폴더에서 이미지 파일 목록 검색 ---");
        List<File> imagesInNonExistentDir = ImageFileLister.listImageFiles(nonExistentDir);
        if (imagesInNonExistentDir.isEmpty()) {
            System.out.println("'" + nonExistentDir + "' 폴더에 이미지 파일이 없거나 폴더가 존재하지 않습니다.");
        }

        // 3. 파일 경로를 디렉토리로 지정하는 경우 테스트
        String filePathAsDir = new File(testDir, "image1.jpg").getAbsolutePath();
        System.out.println("\n--- '" + filePathAsDir + "' (파일) 경로에서 이미지 파일 목록 검색 ---");
        List<File> imagesFromFilePath = ImageFileLister.listImageFiles(filePathAsDir);
        if (imagesFromFilePath.isEmpty()) {
            System.out.println("'" + filePathAsDir + "'는 디렉토리가 아니므로 이미지 파일을 찾을 수 없습니다.");
        }

        // --- 테스트 디렉토리 및 더미 파일 정리 (예시용, 실제 사용시 필요 없음) ---
        System.out.println("\n테스트 디렉토리 정리 중...");
        if (testDir.exists()) {
            for (File file : testDir.listFiles()) {
                file.delete();
            }
            testDir.delete();
            System.out.println("테스트 디렉토리 '" + testDirName + "' 삭제 완료.");
        }
        // -----------------------------------------------------------------
    }
}