package com.github.tommorowsolutions.rkpRestBackend.controllers.mvc;

import com.github.tommorowsolutions.rkpRestBackend.models.GuardedObject;
import com.github.tommorowsolutions.rkpRestBackend.repositories.GuardedObjectRepository;
import com.github.tommorowsolutions.rkpRestBackend.repositories.page.GuardedObjectPageRepository;
import com.github.tommorowsolutions.rkpRestBackend.utils.FileUploadUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/guardedobjects")
public class GuardedObjectMvcController {
    private final GuardedObjectRepository guardedObjectRepository;
    private final GuardedObjectPageRepository guardedObjectPageRepository;

    public GuardedObjectMvcController(GuardedObjectRepository guardedObjectRepository, GuardedObjectPageRepository guardedObjectPageRepository) {
        this.guardedObjectRepository = guardedObjectRepository;
        this.guardedObjectPageRepository = guardedObjectPageRepository;
    }
    @GetMapping("/")
    public String Index(Model model){
        var list = guardedObjectPageRepository.findTop5ByOrderByIdDesc(PageRequest.of(0,5));
        model.addAttribute("gObjects",list);
        return "guardedobjects/Index";
    }
    @GetMapping("/loadall")
    public String IndexLoadAll(Model model){
        model.addAttribute("gObjects", guardedObjectRepository.findAll());
        return "guardedobjects/Index";
    }
    @GetMapping("/details/{id}")
    public String Details(Model model, @PathVariable("id") Long id){
        var optObj = guardedObjectRepository.findById(id);
        if (optObj.isEmpty()) return "redirect:/guardedobjects";
        model.addAttribute("gObject",optObj.get());
        return "guardedobjects/Detail";
    }
    @GetMapping("/new")
    public String Create(Model model){
        GuardedObject gObject = new GuardedObject();
        model.addAttribute("gObject",gObject);
        return "guardedobjects/create";
    }
    @PostMapping("/new")
    public String Create(@ModelAttribute GuardedObject guardedObject,
                         @RequestParam("photo") MultipartFile multipartFile
    ) throws IOException {
        if(!multipartFile.isEmpty()){
            guardedObject.setImage(multipartFile.getOriginalFilename());
            guardedObjectRepository.save(guardedObject);
            try{
                File saveFile=new ClassPathResource("static/img").getFile();
                Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename());
                System.out.println(path);
                Files.copy(multipartFile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            guardedObject.setImage(null);
            guardedObjectRepository.save(guardedObject);
        }

        return "redirect:/guardedobjects/";
    }
    @GetMapping("/deleteimage/{id}")
    public String DeleteImage(@PathVariable("id") Long id) throws IOException {
        var optObj = guardedObjectRepository.findById(id);
        if (optObj.isEmpty()) return "redirect:/guardedobjects";
        GuardedObject guardedObject = optObj.get();
        File saveFile=new ClassPathResource("static/img").getFile();
        Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+guardedObject.getImage());
        if(Files.deleteIfExists(path)) guardedObject.setImage(null);
        guardedObjectRepository.save(guardedObject);
        return "redirect:/guardedobjects/details/"+guardedObject.getId();
    }
    @GetMapping("/update/{id}")
    public String Update(@PathVariable("id") Long id,Model model){
        var optObj = guardedObjectRepository.findById(id);
        if (optObj.isEmpty()) return "redirect:/guardedobjects";
        model.addAttribute("gObject",optObj.get());
        return "guardedobjects/Update";
    }
    @PostMapping("/update")
    public String Update(@ModelAttribute GuardedObject guardedObject,
                         @RequestParam("photo") MultipartFile multipartFile
    ){
        if(!multipartFile.isEmpty()){
            guardedObject.setImage(multipartFile.getOriginalFilename());
            guardedObjectRepository.save(guardedObject);
            try{
                File saveFile=new ClassPathResource("static/img").getFile();
                Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename());
                System.out.println(path);
                Files.copy(multipartFile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            guardedObject.setImage(null);
            guardedObjectRepository.save(guardedObject);
        }

        return "redirect:/guardedobjects/";
    }
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Long id, Model model){
        var optObj = guardedObjectRepository.findById(id);
        if (optObj.isEmpty()) return "redirect:/guardedobjects";
        model.addAttribute("gObject",optObj.get());
        return "guardedobjects/Delete";
    }
    @PostMapping("/confirmdelete/{id}")
    public String DeleteConfirmed(@PathVariable("id") Long id) throws IOException {
        var optObj = guardedObjectRepository.findById(id);
        if (optObj.isEmpty()) return "redirect:/guardedobjects";
        GuardedObject guardedObject = optObj.get();
        File saveFile=new ClassPathResource("static/img").getFile();
        Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+guardedObject.getImage());
        if(Files.deleteIfExists(path)) guardedObject.setImage(null);
        guardedObjectRepository.deleteById(id);
        return "redirect:/guardedobjects/";
    }
}
