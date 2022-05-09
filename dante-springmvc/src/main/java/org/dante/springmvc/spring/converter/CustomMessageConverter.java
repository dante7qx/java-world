package org.dante.springmvc.spring.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.dante.springmvc.web.converter.domain.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * HttpMessageConverter: 处理request和response里的数据
 * 
 * @author dante
 *
 */
public class CustomMessageConverter extends
		AbstractHttpMessageConverter<Person> {

	public CustomMessageConverter() {
		super(new MediaType("application", "x-dante", Charset.forName("UTF-8")));
	}

	/**
	 * 只处理Person类
	 */
	@Override
	protected boolean supports(Class<?> clazz) {
		return Person.class.isAssignableFrom(clazz);
	}

	/**
	 * 处理请求的数据
	 */
	@Override
	protected Person readInternal(Class<? extends Person> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		String tmp = StreamUtils.copyToString(inputMessage.getBody(),
				Charset.forName("UTF-8"));
		String[] tmpArr = tmp.split("-");
		return new Person(new Integer(tmpArr[0]), tmpArr[1]);
	}

	/**
	 * 处理如何输出数据到response
	 */
	@Override
	protected void writeInternal(Person t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String out = "[" + t.getId() + "-->" + t.getName() + "]";
		outputMessage.getBody().write(out.getBytes());
	}

}
