/**
 * Copyright (c) 213Team
 *
 * @className : com.android.volley.toolbox.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/2		WangJY		1.0.0		create
 */

package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonObjectRequestForm extends JsonRequestForm<JSONObject>
{
	private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

	public JsonObjectRequestForm(int method, String url, Map<String, String> requestBodyMap, Response.Listener<JSONObject> listener,
								 Response.ErrorListener errorListener)
	{
		super(method, url, requestBodyMap, listener, errorListener);
	}

	public JsonObjectRequestForm(String url, Map<String, String> requestBodyMap, Response.Listener<JSONObject> listener, Response
			.ErrorListener errorListener)
	{
		super(requestBodyMap == null ? Method.GET : Method.POST, url, requestBodyMap, listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
	{
		try
		{
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, getParamsEncoding()));
			return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		}
		catch (UnsupportedEncodingException e)
		{
			return Response.error(new ParseError(e));
		}
		catch (JSONException je)
		{
			return Response.error(new ParseError(je));
		}
	}
}
