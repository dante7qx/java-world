package org.dante.springmvc.spring.advice;

import org.apache.shiro.authz.UnauthorizedException;
import org.dante.springmvc.spring.exception.DanteException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对于控制器的全局配置
 * 
 * @author dante
 *
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

	/**
	 * @ExceptionHandler: 全局处理控制器里的异常
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {UnauthorizedException.class, DanteException.class})
	public ModelAndView exception(Exception exception, WebRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		String errorMsg = exception.getMessage();
		if(exception instanceof UnauthorizedException) {
			errorMsg = "没有权限，请联系系统管理员！";
		} 
		modelAndView.addObject("errorMsg", errorMsg);
		return modelAndView;
	}
	
	/**
	 * @ModelAttribute: 绑定键值对到Model里
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
	//	model.addAttribute("msg", "额外信息");
	}
	
	/**
	 * @InitBinder，设置WebDataBinder（自动绑定前台请求的参数到Model中）
	 * 
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	//	System.out.println(webDataBinder.getObjectName());
	}
	
}
