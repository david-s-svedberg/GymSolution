package com.dosolves.gym;

import android.database.Cursor;

public interface DataAccess {

	Cursor get(String type);

}
