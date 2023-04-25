package edu.kh.comm.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@SessionAttributes({"loginMember"}) // Model에 추가된 값의 key와 어노테이션에 작성된 값이 같으면
									// 해당 값을 session scope로 이동시키는 역할

public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//private MemberService service = new MemberServiceImpl();
	
	@Autowired // bean으로 등록된 객체 중 타입이 같거나, 상속간계인 bean을 주입함
	private MemberService service; // IOC(제어의 역전) + 의존성 주입(DI)
	
	
	
	
	
	
	
	
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
	public String login( @ModelAttribute Member inputMember,
							Model model,
							RedirectAttributes ra,
							HttpServletResponse resp,
							HttpServletRequest req,
							@RequestParam(value="saveId", required=false) String saveId) {
		logger.info("로그인 기능 수행 됨");
		
		// 아이디, 비밀번호가 일치하는 회원 정보를 조회하는 service 호출 후 결과 반환 받기
		Member loginMember = service.login(inputMember);
		
		/* Model : 데이터를 맵 형식(K:V) 형태로 담아 전달하는 용도의 객체
		 * -> request, session 을 대체하는 객체
		 * 
		 * 
		 * - 기본 scope : request
		 * - session scope로 변환하고 싶은 경우
		 * 클래스 레벨로 @SessionAttibute 를 작성하면 된당.
		 * 
		 */
		
		if(loginMember != null) { // 로그인 성공 시
			model.addAttribute("loginMember", loginMember);
			
			// 로그인 성공 시 무조건 쿠키 생성
			// 단, 아이디 저장 체크 여부에 따라 쿠키의 유지 시간을 조정
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
			if(saveId != null) {// 아이디 저장이 체크 되었을 때
				
				cookie.setMaxAge(60 * 60 * 24 * 365); // 초 단위로 지정 (1년)
				
			} else { // 체크 안되있을 때
				cookie.setMaxAge(0); // 0초 -> 생성되자마자 사라짐 == 쿠키 삭제.
				
			}
			
			// 쿠키가 적용될 범위 지정
			cookie.setPath(req.getContextPath());
			
			// 쿠키를 응답 시 클라이언트에게 전달
			resp.addCookie(cookie);
			
		}else {
			//model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다람쥐.");
			// addFlashAttribute - session쪽에 갔다가 redirect일때 나는 request다 하고 돌아옴
		}
		
		model.addAttribute("loginMember", loginMember);
		
		
		
		
		
		
		return "redirect:/";
		
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout( /* Http Session */
								SessionStatus status) {
		
		// 로그아웃 == 세션을 없애는 것
		
		// * @SessionAttribute를 이용해 session scope에 배치된 데이터는
		// 	  SessionStatus 라는 별도 객체를 이용해야만 없앨 수 있다.
		logger.info("로그아웃 수행 됨");
		
		// Session.invalidate(); // 기존 세션 무효화 방식으로는 안된당!
		status.setComplete(); // 세션의 할 일이 완료 됨 - > 없앰
		return "redirect:/";
	}
	
	// 회원 가입 화면 전환
	@GetMapping("/signUp") // Get 방식: /comm/member/signUp 요청
	public String signUp() {
		
		
		return "member/signUp";
	}
	
}











