package exp17d.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/rest/v1/provider")
public class ProviderRestController {
	private static final Logger logger = LoggerFactory.getLogger(ProviderRestController.class);
       
	// curl -i -X GET http://localhost:8889/rest/v1/provider/echoMessage?msg=Hi
	@RequestMapping(value="/echoMessage", method=RequestMethod.GET)
//	@RequestMapping("/echoMessage") // NOTE ilker, equivalent to above line since default is GET. NOTE ilker WRONG to use when you enable swagger, because swagger thinks all operations(GET,POST,PUT,DELETE,PATH) are there
//	@GetMapping("/echoMessage")		// NOTE ilker, equivalent to above line as well
	/**
	 * http://localhost:8889/rest/v1/provider/echoMessage?msg=Hi
	 */
	// NOTE ilker below optional swagger annotation @ApiOperation can be used to better describe(instead of default of providing method name) what the api end point(resource) does
	@ApiOperation(value = "To test this REST end point is alive via echo",
				  notes = "An optional message value can be passed into this echo service, via msg query param, to use in echoed String",
				  response = String.class)
	public String echoMessage(@ApiParam(value = "optional message value to pass", required = false) @RequestParam(value="msg", defaultValue="Hello ilker") String message) {
	// NOTE ilker below is without optional swagger api param info. Above is equivalent to below enhanced with swagger param info
//	public String echoMessage(@RequestParam(value="msg", defaultValue="Hello ilker") String message) {
		logger.debug("echoMessage with message:{}", message);
		return "echoMessage echoed: " + message;
	}
	
	// curl -i -X GET http://localhost:8889/rest/v1/provider/echoMessage4RolePROVIDER_ADMIN?msg=Hi
	@RequestMapping(value="/echoMessage4RolePROVIDER_ADMIN", method=RequestMethod.GET)
//	@GetMapping("/echoMessage4RolePROVIDER_ADMIN")		// NOTE ilker, equivalent to above line
	/**
	 * http://localhost:8889/rest/v1/provider/echoMessage4RolePROVIDER_ADMIN?msg=Hi
	 */
	// NOTE ilker below optional swagger annotation @ApiOperation can be used to better describe(instead of default of providing method name) what the api end point(resource) does
	@ApiOperation(value = "To test logged in user with PROVIDER_ADMIN role can invoke this REST end point",
				  notes = "An optional message value can be passed into this echo service, via msg query param, to use in echoed String",
				  response = String.class)
	public String echoMessage4RolePROVIDER_ADMIN(@ApiParam(value = "optional message value to pass", required = false) @RequestParam(value="msg", defaultValue="Hello ilker") String message) {
	// NOTE ilker below is without optional swagger api param info. Above is equivalent to below enhanced with swagger param info
//	public String echoMessage(@RequestParam(value="msg", defaultValue="Hello ilker") String message) {
		logger.debug("echoMessage4RolePROVIDER_ADMIN with message:{}", message);
		return "echoMessage echoed: " + message;
	}
	
}