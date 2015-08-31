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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

public abstract class JsonRequestForm<T> extends Request<T>
{
	private final Response.Listener<T> mListener;
	private final Map<String, String>  mRequestBody;

	/** Content type for request. */
	protected static final String PROTOCOL_CHARSET      = "utf-8";
	private static final   String PROTOCOL_CONTENT_TYPE = String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);

	public JsonRequestForm(String url, Map<String, String> requestBodyMap, Response.Listener<T> listener, Response.ErrorListener
			errorListener)
	{
		this(Method.DEPRECATED_GET_OR_POST, url, requestBodyMap, listener, errorListener);
	}

	public JsonRequestForm(int method, String url, Map<String, String> requestBodyMap, Response.Listener<T> listener, Response
			.ErrorListener errorListener)
	{
		super(method, url, errorListener);
		mListener = listener;
		mRequestBody = requestBodyMap;
	}

	@Override
	protected void deliverResponse(T response)
	{
		mListener.onResponse(response);
	}

	@Override
	abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

	@Override
	protected Map<String, String> getParams() throws AuthFailureError
	{
		return mRequestBody;
	}

	@Override
	public String getPostBodyContentType() {
		return getBodyContentType();
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

}
