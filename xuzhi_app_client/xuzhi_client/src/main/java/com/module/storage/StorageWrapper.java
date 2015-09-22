/**
 * Copyright (c) 213Team
 *
 * @className : com.taixinkanghu.app.model.storage.${type_name}
 * @version : 1.0.0
 * @author : WangJY
 * @description : ${TODO}
 * <p>
 * Modification History:
 * Date         	Author 		Version		Description
 * ----------------------------------------------------------------
 * 2015/8/5		WangJY		1.0.0		create
 */

package com.module.storage;

import android.content.Context;

public class StorageWrapper
{
	private static StorageWrapper   s_storageWrapper   = new StorageWrapper();
	private        OwnerPreferences m_ownerPreferences = new OwnerPreferences();

	private StorageWrapper()
	{
	}

	public static StorageWrapper GetInstance()
	{
		return s_storageWrapper;
	}

	public void init(Context context)
	{
		m_ownerPreferences.init(context);
	}

	public OwnerPreferences getOwnerPreferences()
	{
		return m_ownerPreferences;
	}
}
