package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.Reserve;
import com.catchmind.catchtable.domain.type.ReservationType;
import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.dto.ReserveDto;

import com.catchmind.catchtable.dto.network.request.ReviewRequest;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.repository.ReserveRepository;
import com.catchmind.catchtable.service.MydiningService;
import com.catchmind.catchtable.service.ProfileLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("mydining")
@RequiredArgsConstructor
public class MydiningController {

    private final ReserveRepository reserveRepository;
    private final MydiningService mydiningService;
    private final ProfileLogicService profileLogicService;


    @GetMapping("/planned")
    public String planned(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<Reserve> reserves = reserveRepository.findAllByresStatusAndProfile_PrIdx(ReservationType.PLANNED, prIdx);
        System.out.println(reserves);
        model.addAttribute("list", reserves);
        return "/mydining/planned";
    }

//    @GetMapping("/collection")
//    public ModelAndView myCollection(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
//        Long prIdx = catchPrincipal.prIdx();
//        System.out.println(catchPrincipal.prIdx());
//        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
//        ModelAndView modelAndView = new ModelAndView("/mypage/mycollection");
//        modelAndView.addObject("profile",profile);
//        return modelAndView;
//    }

    @GetMapping("/reserve/plannedDetail/{resIdx}")
    public String detail(Model model, @PathVariable("resIdx") Long resIdx) {
        System.out.println(resIdx);
        ReserveDto reserveDto = mydiningService.getDetail(resIdx);
        model.addAttribute("detail", reserveDto);
        System.out.println("reserveDTO->" + reserveDto);
        return "/mydining/plannedDetail";
    }

//    @GetMapping("/reserve/bistroDetail/{resIdx}")
//    public String bistroDetail(Model model, @PathVariable("resIdx") Long resIdx) {
//        System.out.println(resIdx);
//        ReserveDto reserveDto = mydiningService.getDetail(resIdx);
//        model.addAttribute("detail", reserveDto);
//        System.out.println("reserveDTO->" + reserveDto);
//        return "/mydining/bistroDetail";
//    }


    @GetMapping("/done")
    public String done(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        List<Reserve> reserves = reserveRepository.findAllByresStatusAndProfile_PrIdx(ReservationType.DONE, prIdx);
        System.out.println(reserves);
        model.addAttribute("list", reserves);
//        model.addAttribute()
        return "mydining/done";
    }

    @GetMapping("/reserve/doneDetail/{resIdx}")
    public String doneDetail(Model model, @PathVariable("resIdx") Long resIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        System.out.println("🍎" + resIdx);
        Long prIdx = catchPrincipal.prIdx();
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ReserveDto reserveDto = mydiningService.getDetail(resIdx);
        model.addAttribute("profile", profile);
        model.addAttribute("detail", reserveDto);
        model.addAttribute("resIdx", resIdx);
        System.out.println("reserveDTO->" + reserveDto);
        return "/mydining/doneDetail";
    }


    @PostMapping("/reserve/doneDetail")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("files") List<MultipartFile> files, ReviewRequest review) throws IOException {
        System.out.println("💵 -> " +review);
        Long saveIdx = mydiningService.saveReview(review);
        for (MultipartFile multipartFile : files) {
            mydiningService.saveFile(multipartFile, saveIdx);
        }
        System.out.println("⭕" + file);
        System.out.println("🎁" + files);
        return "redirect:/mydining/done";
    }

    @PostMapping("/reserve/plannedDetail/{resIdx}")
    public String updateCancel(Model model, @RequestParam(name="resIdx")Long plannedIdx, @PathVariable("resIdx") Long resIdx)  {
//        reserveDto = mydiningService.getDetail(resIdx);
//        model.addAttribute("resIdx", resIdx);
        mydiningService.updateCancel(plannedIdx);
        System.out.println("🍊" + resIdx);
        return "redirect:/mydining/cancel";
    }

//    @PostMapping("/reserve/plannedDetail")
//    public String updateCancel(Model model, ReserveDto reserveDto)  {
////        ReserveDto reserveDto = mydiningService.getDetail(resIdx);
////        model.addAttribute("resIdx", resIdx);
//        mydiningService.updateCancel(reserveDto);
//        return "redirect:/mydining/cancel";
//    }

    @GetMapping("/cancel")
    public String cancel(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<Reserve> reserves = reserveRepository.findAllByresStatusAndProfile_PrIdx(ReservationType.CANCEL, prIdx);
        System.out.println(reserves);
        model.addAttribute("list", reserves);
        return "mydining/cancel";
    }

    @GetMapping("/emptySlot")
    public ModelAndView emptySlot() {
        return new ModelAndView("mydining/emptySlot");
    }
    @GetMapping("/reserveOpen")
    public ModelAndView reserveOpen() {
        return new ModelAndView("mydining/reserveOpen");
    }

    @GetMapping("/notifications")
    public ModelAndView notifications() {
        return new ModelAndView("mydining/notifications");
    }
}
