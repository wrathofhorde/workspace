package com.wrath;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opencv.core.Core;

import com.wrath.processing.IDForgeryDetection;
import com.wrath.utils.ImageFileLister;
import com.wrath.utils.PathUtil;

public class Main {
    public static void testIDForgeryDetection() {
        IDForgeryDetection fd = new IDForgeryDetection();
        fd.loadImage("null");

        if (fd.isImageEmpty()) {
            System.out.println("이미지 로딩 실패");
            return;
        }

        if (fd.hasFlatHistogram()) {
            System.out.println("⚠ 색상 히스토그램 분석 결과: 복사본 또는 위변조 의심");
        }

        if (fd.hasMoiréPattern()) {
            System.out.println("⚠ 푸리에 분석 결과: 화면 촬영 흔적 또는 모아레 패턴 감지됨");
        }
    }

    public static void main(String[] args) throws IOException {
        PathUtil.test();

        List<File> images = ImageFileLister.listImageFiles("test_images");

        for (File image : images) {
            // System.out.println(image.getName());
            System.out.println(image.getAbsolutePath());
        }
    }
}