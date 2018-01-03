package com.offcn.test;

import org.junit.Test;

import com.offcn.bean.FaceIdentifyBean;
import com.offcn.enums.FaceConstant;
import com.offcn.service.FaceService;
import com.offcn.service.impl.FaceServiceImpl;
import com.offcn.utils.FileUtils;

public class App {
	
	/**
	 * 
	 * @TODO:  测试人脸识别
	 */
	@Test
	public void test3() {
		
		FaceIdentifyBean faceIdentifyBean = new FaceIdentifyBean();
		
		faceIdentifyBean.setAccess_token(FaceConstant.access_token.getVal());
		
		faceIdentifyBean.setGroup_id("java0815");
		faceIdentifyBean.setImages(FileUtils.getPicStrByImages("D:/BaiduYunDownload/001.jpg"));
		
		FaceService faceService = new FaceServiceImpl();
		
		FaceIdentifyBean identifyFace = faceService.identifyFace(faceIdentifyBean);
		
		System.out.println(identifyFace);
	}
	
}
