package com.wrath.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 현재 Java 애플리케이션의 실행 경로를 얻는 유틸리티 클래스입니다.
 */
public class PathUtil {

    /**
     * JVM의 'user.dir' 시스템 속성을 사용하여 현재 작업 디렉토리(Current Working Directory)를 반환합니다.
     * 대부분의 경우, 이 메서드가 원하는 '현재 실행 경로'를 제공합니다.
     *
     * @return 현재 작업 디렉토리의 절대 경로 문자열
     */
    public static String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * '.' (현재 디렉토리) File 객체를 사용하여 현재 디렉토리의 정식(canonical) 경로를 반환합니다.
     * 심볼릭 링크를 해석하고 '..' 같은 상대 경로를 제거한 깔끔한 절대 경로를 제공합니다.
     * IOException이 발생할 수 있으므로 호출하는 쪽에서 처리해야 합니다.
     *
     * @return 현재 디렉토리의 정식 절대 경로 문자열
     * @throws IOException 경로를 확인할 수 없거나 접근 권한이 없을 때 발생
     */
    public static String getCanonicalPath() throws IOException {
        return new File(".").getCanonicalPath();
    }

    /**
     * '.' (현재 디렉토리) File 객체를 사용하여 현재 디렉토리의 절대 경로를 반환합니다.
     * getCanonicalPath()와 달리 심볼릭 링크를 해석하거나 '..' 등을 제거하지 않을 수 있습니다.
     *
     * @return 현재 디렉토리의 절대 경로 문자열
     */
    public static String getAbsolutePath() {
        return new File(".").getAbsolutePath();
    }

    /**
     * 현재 클래스 파일 또는 JAR 파일이 로드된 위치를 반환합니다.
     * 이는 '현재 실행 경로'보다는 애플리케이션의 '원본' 위치에 더 가깝습니다.
     * JAR 파일로 실행될 경우 해당 JAR 파일의 경로를 반환할 수 있습니다.
     *
     * @param clazz 경로를 알고 싶은 클래스 객체 (예: YourClass.class)
     * @return 클래스가 로드된 위치의 경로 문자열 (URL 인코딩될 수 있음)
     */
    public static String getClassLocation(Class<?> clazz) {
        URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
        if (location != null) {
            return location.getPath();
        }
        return null; // 또는 적절한 예외 처리
    }

    // 간단한 테스트를 위한 main 메서드 (실제 애플리케이션에서는 필요에 따라 호출)
    public static void test() {
        System.out.println("--- 현재 실행 경로 정보 ---");
        System.out.println("1. System.getProperty(\"user.dir\"): " + PathUtil.getCurrentWorkingDirectory());

        try {
            System.out.println("2. File(\".\").getCanonicalPath(): " + PathUtil.getCanonicalPath());
        } catch (IOException e) {
            System.err.println("2. 경로를 얻는 중 오류 발생 (getCanonicalPath): " + e.getMessage());
        }

        System.out.println("3. File(\".\").getAbsolutePath(): " + PathUtil.getAbsolutePath());
        System.out.println("4. Class Location (PathUtil.class): " + PathUtil.getClassLocation(PathUtil.class));
    }
}
