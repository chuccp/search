package com.kanke.search.query.function;

import java.io.IOException;
import java.util.Date;

public abstract class DateDocValues  {

	public DateDocValues() {
	}


	
	public abstract long DateFunc(Date date) throws IOException;

}
