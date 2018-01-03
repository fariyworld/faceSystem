package com.offcn.service.impl;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.offcn.bean.FaceAccessToken;
import com.offcn.bean.FaceIdentifyBean;
import com.offcn.bean.FaceRegisterBean;
import com.offcn.bean.FaceUpdateBean;
import com.offcn.enums.FaceConstant;
import com.offcn.service.FaceService;
import com.offcn.utils.HttpClientUtil;

public class FaceServiceImpl implements FaceService {

	@Override
	public FaceAccessToken getAccessToken(String client_id, String client_secret) {

		System.out.println("请求的内容：" + client_id);
		System.out.println("请求的内容：" + client_secret);

		String url = FaceConstant.accessTokenUrl.getVal() + "client_id=" + client_id + "&client_secret="
				+ client_secret;

		FaceAccessToken faceAccessToken = HttpClientUtil.doPost(FaceAccessToken.class, url);

		if (faceAccessToken == null) {

			ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
			builder.type("application/xml");
			builder.entity("<error>faceAccessToken Not Found</error>");
			throw new WebApplicationException(builder.build());

		} else {

			System.out.println("获取到的key：" + faceAccessToken.getAccess_token());

			return faceAccessToken;
		}
	}

	/**
	 * HTTP方法：POST
	 * 请求URL：https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add
	 * Header如下：Content-Type application/x-www-form-urlencoded
	 * URL参数：access_token Body中放置请求参数： 上面两个封装在FaceRegisterBean 其他的固定
	 */
	@Override
	public FaceRegisterBean addFace(FaceRegisterBean faceRegisterBean) {

		String url = FaceConstant.registerUrl.getVal();

		url = url + "?access_token=" + faceRegisterBean.getAccess_token();

		faceRegisterBean.setAccess_token(null);

		return HttpClientUtil.doPost(url, faceRegisterBean, HttpClientUtil.setCommonHeader());
	}

	/**
	 * HTTP方法：POST 请求URL： https://aip.baidubce.com/rest/2.0/face/v2/identify
	 * URL参数：access_token 通 过 API Key 和 Secret Key 获 取 的 access_token
	 * Header如下：Content-Type application/x-www-form-urlencoded
	 * Body中放置请求参数，参数详情如下： group_id images 必选 ext_fields user_top_num 可选 返回参数
	 */
	@Override
	public FaceIdentifyBean identifyFace(FaceIdentifyBean identifyBean) {
		
		System.out.println("客户端对象组："+ identifyBean.getGroup_id());

		String url = FaceConstant.identifyUrl.getVal();

		url = url + "?access_token=" + identifyBean.getAccess_token();

		identifyBean.setAccess_token(null);

		FaceIdentifyBean faceIdentifyBean = HttpClientUtil.doPost(url, identifyBean, HttpClientUtil.setCommonHeader());
	
		if (faceIdentifyBean == null) {

			ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
			builder.type("application/xml");
			builder.entity("<error>identifyBean Not Found</error>");
			throw new WebApplicationException(builder.build());

		} else {
			
			System.out.println("服务器验证结果" + faceIdentifyBean);
			//处理resultbean
			
			faceIdentifyBean.setResultBean(HttpClientUtil.toResultBean(faceIdentifyBean.getResult()));
			
			return faceIdentifyBean;
		}
	}

	/**
	 * HTTP方法：POST 请求URL：
	 * https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update
	 * URL参数：access_token 通 过 API Key 和 Secret Key 获 取 的 access_token
	 * Header如下：Content-Type application/x-www-form-urlencoded
	 * Body中放置请求参数，参数详情如下： uid images 返回参数:log_id
	 */
	@Override
	public FaceUpdateBean updateFace(FaceUpdateBean faceUpdateBean) {

		String url = FaceConstant.updateUrl.getVal();

		url = url + "?access_token=" + faceUpdateBean.getAccess_token();

		faceUpdateBean.setAccess_token(null);

		return HttpClientUtil.doPost(url, faceUpdateBean, HttpClientUtil.setCommonHeader());
	}

}
