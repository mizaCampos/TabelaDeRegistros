package com.miza.sprimgmvc.TabelaDeRegistros.controllers;

import com.miza.sprimgmvc.TabelaDeRegistros.dto.RequisicaoFormProfessor;
import com.miza.sprimgmvc.TabelaDeRegistros.models.Professor;
import com.miza.sprimgmvc.TabelaDeRegistros.models.StatusProfessor;
import com.miza.sprimgmvc.TabelaDeRegistros.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/professores")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;


    @GetMapping("")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao){
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("statusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("\n********* TEM ERROS *********");

            ModelAndView mv = new ModelAndView("/professores/new");
            mv.addObject("statusProfessor", StatusProfessor.values());
            return mv;

        }else{
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor);

            return new ModelAndView("redirect:/professores/" + professor.getId());
        }
    }
    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if(optional.isPresent()){
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);

            return mv;
        }else{
            // não achou um registro na tabela professor com o id informado
            System.out.println("****Não achou o professor de ID: " + id + " ****");
            return new ModelAndView("redirect:/professores");
        }
    }
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if(optional.isPresent()){
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);

            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", professor.getId());

        }
        else{
            System.out.println("****Não achou o professor de ID: " + id + " ****");
            ModelAndView mv = new ModelAndView("redirect/professores");
            mv.addObject("mensagem", "Professor " + id + " Não encontrado no banco!");
            mv.addObject("erro", true);
            return mv;

        }
        ModelAndView mv = new ModelAndView("professores/edit");
        mv.addObject("statusProfessor", StatusProfessor.values());

        return mv;
    }

    @PostMapping("/{id}")
    public  ModelAndView update(@PathVariable Long  id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           System.out.println("\n***** TEM ERROS *********");

            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("statusProfessor", StatusProfessor.values());
            return mv;

        }else {
            Optional<Professor> optional = this.professorRepository.findById(id);

            if(optional.isPresent()){
                Professor professor2 = requisicao.toProfessor(optional.get());
                return new ModelAndView("redirect:/professores");
            }
            else{
                System.out.println("\n******* NÃO FOI ENCONTRADO O PROFESSOR COM O ID INFORMADO " + id + " *******");
                ModelAndView mv = new ModelAndView("redirect/professores");
                mv.addObject("mensagem", "Professor " + id + " UPDATE error!");
                mv.addObject("erro", true);
                return mv;

            }
        }

    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/professores");

        try{
            this.professorRepository.deleteById(id);
            
            mv.addObject("mensagem", "Professor " + id + " deletado com sucesso");
            mv.addObject("erro", false);

        }
        catch (EmptyResultDataAccessException e){
            System.out.println(e);
            mv = this.retornaErroProfessores("DELETE ERROR: Professor " + id + " Não encontrado no banco!");
        }
        return mv;
    }

    private ModelAndView retornaErroProfessores(String msg){
        ModelAndView mv = new ModelAndView("redirect/professores");
        mv.addObject("mensagem",msg);
        mv.addObject("erro",true);
        return mv;

    }


}
