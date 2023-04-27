package kr.co.member.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.admin.model.service.AdminService;
import kr.co.admin.model.vo.OrderPageData;
import kr.co.member.model.service.MemberService;
import kr.co.member.model.vo.Member;
import kr.co.member.model.vo.Order;
import kr.co.member.model.vo.WishList;

@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private AdminService aService;

	public MemberController() {
		super();
	}
	
	@RequestMapping(value = "/loginFrm.do")
	public String loginFrm(HttpSession session) {
		Member me = (Member)session.getAttribute("m");
		if(me == null) {
			return "member/login";
		}else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/login.do")
	public String loginMember(Member m,HttpSession session,Model model) {
		Member member = service.loginMember(m);
		System.out.println("member : "+member);
		if(member != null) {
			if(member.getMemberGrade() != 4) {
			session.setAttribute("m", member);
			return "redirect:/";
			}else{
				model.addAttribute("title","로그인 실패");
				model.addAttribute("msg","탈퇴한 계정입니다.새로운 계정을 가입해주세요");
				model.addAttribute("icon","error");
				model.addAttribute("loc","/joinMemberFrm.do");
				return "common/msg";
			}
		}else {
			model.addAttribute("title","로그인 실패");
			model.addAttribute("msg","아이디 비밀번호를 확인해 주세요");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/loginFrm.do");
			return "common/msg";
		}
	}
		
	@RequestMapping(value = "/joinMemberFrm.do")
	public String joinMemberFrm() {
		return "member/joinMemberFrm";
	}
	
	@ResponseBody	
	@RequestMapping(value = "/joinSendMail.do")
	public String joinSendMail(String email) {
		return mailSender.sendMail(email);
	}
	
	@RequestMapping(value= "/joinMember.do")
	public String joinMember(Member m,Model model) {
		int result = service.joinMember(m);
		if(result == 0) {
			model.addAttribute("title","회원가입 실패");
			model.addAttribute("msg","회원가입에 실패했습니다. 관리자에게 문의하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/joinMemberFrm.do");
			return "common/msg";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idChk.do")
	public String idChk(String memberId) {
		Member m = service.idChk(memberId);
		if(m != null) {
			return m.getMemberId();
		}else {
		return "null";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/findMemberId.do")
	public String findMemberId(Member m) {
		Member member = service.findMemberId(m);
		if(member == null) {
			return "null";
		}else {
			return member.getMemberId();
		}
	}
	
	@RequestMapping(value = "/myPage.do")
	public String myPage(int reqPage,int tabNo,@SessionAttribute (required = false) Member m ,Model model) {
		Member result = service.selectSellerApplication(m.getMemberNo());
		OrderPageData opd = service.selectOrderList(reqPage,m.getMemberNo());
		System.out.println(tabNo);
		
		if(result == null) {
			model.addAttribute("sellerApplication",0);
		}else {
			model.addAttribute("sellerApplication",result.getMemberNo());
		}
		if(!opd.getOrderList().isEmpty()) {
			model.addAttribute("pageNavi",opd.getPageNavi());
			model.addAttribute("list",opd.getOrderList());
			
			if(tabNo == 1) {
				model.addAttribute("tabNo",1);
			}else {
				model.addAttribute("tabNo",0);
			}
		}
		return "member/myPage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/beforePassWord.do")
	public String beforePassWord(Member m) {
		Member member = service.beforePwMember(m);
		if(member == null) {
			return "null";
		}else {
			return "ok";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePassWord.do")
	public String updatePassWord(Member member,@SessionAttribute(required = false) Member m) {
		int result = service.updatePwMember(member);
		if(result != 0) {
			m.setMemberPw(member.getMemberPw());
			return "ok";
		}else {
			return "no";
		}
	}
	
	@RequestMapping(value = "/sellerApplication.do")
	public String sellerApplication(int memberNo,Model model) {
		int result = service.sellerApplication(memberNo); 
		if(result == 0) {
			model.addAttribute("title","판매자 신청 실패");
			model.addAttribute("msg","판매자 신청에 실패했습니다.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			return "common/msg";
		}else {
			model.addAttribute("title","판매자 신청 성공");
			model.addAttribute("msg","판매자 신청을 완료했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			return "common/msg";
		}
	}
	
	@RequestMapping(value = "/cancelSeller.do")
	public String cancelSeller(int memberNo,Model model) {
		int result = service.cancelSeller(memberNo);
		if(result == 0) {
			model.addAttribute("title","판매자 신청 취소 실패");
			model.addAttribute("msg","판매자 신청 취소에 실패했습니다.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			return "common/msg";
		}else {
			model.addAttribute("title","판매자 신청 취소 성공");
			model.addAttribute("msg","판매자 신청을 취소했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			return "common/msg";
		}
	}
	
	@RequestMapping(value = "/dropMember.do")
	public String deleteMember(int memberNo,Model model,HttpSession session) {
		Member m = (Member)session.getAttribute("m");
		if(m.getMemberNo() == memberNo) {
			int result = service.deleteMember(memberNo);
			if(result != 0) {
				model.addAttribute("title","회원탈퇴");
				model.addAttribute("msg","회원 탈퇴에 성공했습니다.");
				model.addAttribute("icon","success");
				model.addAttribute("loc","/");
				session.invalidate();
				return "common/msg";
			}else {
				model.addAttribute("title","회원탈퇴");
				model.addAttribute("msg","회원 탈퇴에 실패했습니다. 관리자에게 문의하세요");
				model.addAttribute("icon","error");
				model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
				return "common/msg";
			}
		}else {
			model.addAttribute("title","회원탈퇴");
			model.addAttribute("msg","본인 아이디만 탈퇴 가능합니다");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/");
			return "common/msg";
		}
	}
	
	@RequestMapping(value =  "/findMemberPw.do")
	public String findMemberPw(Member m,Model model) {
		Member member = service.selectOneMember(m);
		if(member != null) {
			String memberPw = mailSender.sendPw(member.getMemberEmail());
			System.out.println("memberPw : "+memberPw);
			if(memberPw != null) {
				member.setMemberPw(memberPw);
				int result = service.updatePwMember(member);
				if(result != 0) {
					model.addAttribute("title","임시비밀번호 전송완료");
					model.addAttribute("msg","해당 이메일로 임시비밀번호를 전송했습니다.");
					model.addAttribute("icon","success");
					model.addAttribute("loc","/loginFrm.do");
					return "common/msg";
				}
				
			}
		}
		model.addAttribute("title","임시비밀번호 전송실패");
		model.addAttribute("msg","해당 이메일로 임시 비밀번호전송에 실패했습니다.");
		model.addAttribute("icon","error");
		model.addAttribute("loc","/loginFrm.do");
		return "common/msg";
	}


// 상품 등록 페이지 productInsert.jsp를 방문하는 함수.  판매자(grade 2)만 허용됨
	@RequestMapping(value = "/productInsert.do")
	public String productInsert(HttpSession session, Model model) {
		Member me = (Member)session.getAttribute("m");
		if(me != null) {
			if(me.getMemberGrade()==2) {
				return "product/productInsert";
			}else {
				model.addAttribute("title","접근 제한됨");
				model.addAttribute("msg","판매자만 사용할 수 있는 기능입니다.");
				model.addAttribute("icon","error");
				model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
				return "common/msg";
			}
		}else {
			model.addAttribute("title","접근 제한됨");
			model.addAttribute("msg","로그인을 해주십시오.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/loginFrm.do");
			return "common/msg";
		}
	}
	



// 관심상품 등록
	@ResponseBody
	@RequestMapping(value="/insertWishList.do", produces = "application/json;charset=utf-8")
	public String insertWishList(HttpSession session, int house_no, int lesson_no) {
		String message = "";
		Member me = (Member)session.getAttribute("m");
		if(me == null) {
			message = "로그인이 필요합니다.";
		}else {
			if(me.getMemberGrade()!=3) {
				message = "일반회원 로그인이 필요합니다.";
			}else {
				WishList w = new WishList();
				w.setMemberId(me.getMemberId());
				w.setHouse_no(house_no);
				w.setLesson_no(lesson_no);
				message = service.selectMyWishlist(w);
				if(message.equals("ok")) {
					int result = service.insertMyWishlist(w);
					if(result > 0) {
						message = "관심상품에 등록했습니다.";
					}else {
						message = "알 수 없는 이유로 관심상품 등록에 실패했습니다.";
					}
				}
			}
		}
		return message;
	}



// 나의 관심상품 목록 보기
	@RequestMapping(value="/wishList.do")
	public String wishList(HttpSession session, Model model) {
		Member me = (Member)session.getAttribute("m");
		if(me!=null) {
			ArrayList<WishList> allWishList = service.selectAllWishList(me.getMemberId());
			
			model.addAttribute("allWishList", allWishList);
			
			return "member/myWishList";
		}else {
			return "member/login";
		}
	}



// 나의 관심상품 페이지에서 관심상품을 삭제하고 나의 관심상품 페이지를 재방문하기
	@RequestMapping(value="/deleteWishList.do")
	public String deleteWishList(int wishNo) {
		int result = service.deleteWishList(wishNo);
		
		if(result>0) {
			return "redirect:/wishList.do";
		} else {
			return "redirect:/";
		}
	}



	@RequestMapping(value = "/updateMember.do")
	public String updateMember(Member member,Model model,@SessionAttribute(required = false) Member m) {
		int result = service.updateMember(member);
		if(result == 0) {
			model.addAttribute("title","정보수정 실패");
			model.addAttribute("msg","회원 정보 수정에 실패했습니다.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			return "common/msg";
		}else {
			model.addAttribute("title","정보수정 성공");
			model.addAttribute("msg","회원 정보 수정에 성공했습니다.");
			model.addAttribute("icon","success");
			model.addAttribute("loc","/myPage.do?reqPage=1&tabNo=0");
			m.setMemberName(member.getMemberName());
			m.setMemberPhone(member.getMemberPhone());
			return "common/msg";
		}
		
	}
	
	@RequestMapping(value="/myOrderDetail.do")
	public String myPageOrderDetail(int orderNo, Model model) {
		Order orderDetailInfo = aService.selectOrderDetailInfo(orderNo);
		ArrayList<Order> orderDetailList = aService.selectOrderDetail(orderNo);
		int orderDetailCount = aService.selectOrderDetailCount(orderNo);
		model.addAttribute("orderDetailInfo", orderDetailInfo);
		model.addAttribute("orderDetailList", orderDetailList);
		model.addAttribute("orderDetailCount", orderDetailCount);
		
		return "member/orderDetail";
	}

}
