package org.java.world.dante.reactor.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息事件
 * 
 * @author dante
 *
 */
@Data
@AllArgsConstructor
public class MsgEvent {
	private String msg;
	private Date occurTime;

	@Override
	public String toString() {
		return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")) + " - " + msg + "]";
	}

}
