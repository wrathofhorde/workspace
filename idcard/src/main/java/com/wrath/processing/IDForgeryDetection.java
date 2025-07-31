package com.wrath.processing;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IDForgeryDetection {
    // static {
    // // OpenCV 라이브러리 로드
    // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    // }

    private Mat image;

    public boolean isImageEmpty() {
        if (image == null || image.empty()) {
            return true;
        }

        return false;
    }

    // ✅ 색상 히스토그램 분석 (flat color detection)
    public boolean hasFlatHistogram() {
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

        // 히스토그램 계산: Hue 채널만
        List<Mat> hsvChannels = new ArrayList<>();
        Core.split(hsv, hsvChannels);
        Mat hue = hsvChannels.get(0);

        Mat hist = new Mat();
        Imgproc.calcHist(Arrays.asList(hue), new MatOfInt(0), new Mat(), hist, new MatOfInt(50),
                new MatOfFloat(0, 180));

        // 히스토그램 분산 계산
        Core.normalize(hist, hist, 0, 1, Core.NORM_MINMAX);
        double mean = Core.mean(hist).val[0];
        double variance = 0;
        for (int i = 0; i < hist.rows(); i++) {
            double val = hist.get(i, 0)[0];
            variance += Math.pow(val - mean, 2);
        }
        variance /= hist.rows();

        System.out.println("히스토그램 분산: " + variance);
        return variance < 0.005; // 일정 기준 이하일 경우 단조로운 복사 이미지로 간주
    }

    // ✅ 모아레 패턴 또는 고주파 패턴 탐지 (FFT)
    public boolean hasMoiréPattern() {
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        gray.convertTo(gray, CvType.CV_32F); // FFT를 위해 float32로 변환

        // 푸리에 변환
        Mat complex = new Mat();
        List<Mat> planes = Arrays.asList(gray, Mat.zeros(gray.size(), CvType.CV_32F));
        Core.merge(planes, complex);
        Core.dft(complex, complex);

        // 푸리에 스펙트럼 중심 이동
        Core.split(complex, planes);
        Mat mag = new Mat();
        Core.magnitude(planes.get(0), planes.get(1), mag);

        // 로그 스케일
        Core.add(mag, Scalar.all(1), mag);
        Core.log(mag, mag);

        // 중심 이동
        shiftDFT(mag);

        // Normalize for display
        Core.normalize(mag, mag, 0, 1, Core.NORM_MINMAX);

        // 중심에서 벗어난 고주파 패턴 존재 여부 검사
        double highFreqEnergy = Core.mean(mag).val[0];
        System.out.println("주파수 평균 에너지: " + highFreqEnergy);

        return highFreqEnergy > 0.06; // 경험적 임계값
    }

    // 중심 이동 함수 (DFT 결과 이미지 shift)
    public void shiftDFT(Mat mat) {
        int cx = mat.cols() / 2;
        int cy = mat.rows() / 2;

        Mat q0 = new Mat(mat, new Rect(0, 0, cx, cy)); // Top-Left
        Mat q1 = new Mat(mat, new Rect(cx, 0, cx, cy)); // Top-Right
        Mat q2 = new Mat(mat, new Rect(0, cy, cx, cy)); // Bottom-Left
        Mat q3 = new Mat(mat, new Rect(cx, cy, cx, cy)); // Bottom-Right

        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);

        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }

    public Mat loadImage(String imageFile) {
        image = Imgcodecs.imread(imageFile);

        if (image.empty()) {
            System.out.println("이미지 로딩 실패");
        }

        return image;
    }
}
