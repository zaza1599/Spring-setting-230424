package edu.kh.comm.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.comm.member.model.service.MemberService;
import edu.kh.comm.member.model.vo.Member;

// POJO 기반 프레임워크 : 외부 라이브러리 상속 X

// class : 객체를 만들기위한 설계도 
// -> 객체를 생성 되어야 기능 수행이 가능함 new 클래스명()
// --> IOC(제어의 역전, 객체 생명주기를 스프링이 관리)
// ** 이 때, 스프링이 생성한 객체를 bean이라고 한다 **


// bean 등록 == 스프링이 객체로 만들어서 가지고 있어라

// @Component // 해당 클래스를 bean으로 등록하라고 프로그램에게 알려주는 주석(Annotation)

@Controller // 생성된 bean이 Controller임을 명시 + bean 등록

@RequestMapping("/member") // localhost:8080/comm/member 이하의 요청을 처리하는 컨트롤러


public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//private MemberService service = new MemberServiceImpl();
	
	@Autowired // bean으로 등록된 객체 중 타입이 같거나, 상속간계인 bean을 주입함
	private MemberService service; // IOC(제어의 역전)
	
	
	
	
	
	
	
	// Controller : 요청/응답을 제어하는 역할을 지닌 클래스
	
	/* @RequestMapping : 클라이언트 요청(url)에 맞는 클래스 or 메서드를 연결시켜주는 어노테이션
	 * 
	 * [위치에 따른 해석]
	 * - 클래스 레벨 : 공통주소 
	 * - 메서드 레벨 : 공통주소 외 나머지 주소
	 * 
	 * 단, 클래스 레벨에 @RequestMapping이 존재하지 않는다면
	 * - 메서드 레벨 : 단독 요청 처리 주소
	 * 
	 * 
	 * 
	 * [작성법에 따른 해석]
	 *	1) @RequestMapping("url")
	 * --> 요청 방식(GET/POST) 관계 없이 url이 일치하는 요청 처리
	 *	
	 *	2) RequestMapping = "url", method = RequestMethod.GET | POST)
	 * --> 요청 방식에 따라 요청 처리
	 *	
	 *	** 메서드 레벨에서 GET/POST 방식을 구분하여 매핑 **
	 *	@GetMapping("url") / @PostMapping("url")
	 *
	 */
	
	
	
	// 로그인
	// 요청 시 파라미터를 얻어오는 방법 1
	// -> HttpServletRequest 이용
	
	/*@RequestMapping("/login") // localhost:8080/comm/member/login
	// 클래스 레벨 존재하지 않으면 // localhost:8080/comm/login
	public String login(HttpServletRequest req) {
		logger.info(" 로그인 요청 됨 ");
		String inputEmail = req.getParameter("inputEmail");
		String inputPw = req.getParameter("inputPw");
		
		logger.debug("inputEmail:: " + inputEmail);
		logger.debug("inputPw:: " + inputPw);
		return "redirect:/";
		
	}*/
	
	// 요청 시 파라미터를 얻어오는 방법 2 
	// -> @RequestParam 어노테이션 사용
	
	// @RequestParam("name속성값" ) 자료형 변수명
	// -> 클라이언트 요청 시 같이 전달된 파라미터를 변수에 저장
	// -> 어떤 파라미터를 변수에 저장할지는 "name속성값"을 이용해 지정
	
	// @RequestParam을 생략하지만 파라미터를 얻어오는 방법!
	// -> name 속성값과 파라미터를 저장할 변수 이름을 동일하게 작성
	
	// [ 속성 ]
	// value : input 태그의 name 속성값 작성
	
	// requried : 입력된 name 속성값이 필수적으로 파라미터에 포함돼야 하는지를 지정
	//			requried = true / false (기본값 true)
	
	// defaultValue : required false 인 상태에서 파라미터가 존재하지 않을 경우의 값을 지정
	
	
	
//	@RequestMapping("/login")
//	public String login( /*@RequestParam("inputEmail")*/ String inputEmail,
//						/*@RequestParam("inputPw")*/ String inputPw,
//						@RequestParam(value="inputName", required=false, defaultValue = "홍길동") String inputName
//						) {
//		
//		logger.info("로그인 요청됨");
//		
//		logger.debug("email : "  + inputEmail);
//		logger.debug("pw : "  + inputPw);
//		logger.debug("inputName : "  + inputName);
//		
//		
//		return "redirect:/";
//	}
	
	// 요청 시 파라미터를 얻어오는 방법 3
	// -> @ModelAttribute 어노테이션 사용
	
	// [@modelAttribute를 매개변수에 작성하는 경우]
	// @ModelAttribute VO 타입 변수명
	
	// @RequestMapping(value ="/login", method = RequestMethod.POST)
	@PostMapping("/login")
	public String login( @ModelAttribute Member inputMember) {
		
		logger.info("로그인 기능 수행 됨");
		
		// 아이디, 비밀번호가 일치하는 회원 정보를 조회하는 service 호출 후 결과 반환 받기
		Member loginMember = service.login(inputMember);
		
		return "redirect:/";
		
	}
	
	// 회원 가입 화면 전환
	@GetMapping("/signUp") // Get 방식: /comm/member/signUp 요청
	public String signUp() {
		
		
		return "member/signUp";
	}
	
}











