package util;

import java.text.SimpleDateFormat;
import java.util.Date;

// NOVA ALTERACAO
public class Data {

	public String data(String dateTimeFormat) {
        SimpleDateFormat formatas = new SimpleDateFormat(dateTimeFormat);
        String data = formatas.format(new Date());
       return data;
	}
}
