package br.com.springboot.crud_restapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.crud_restapi.model.Usuario;
import br.com.springboot.crud_restapi.repository.UserRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired //IC, CD, ou CDI - injeção de dependencia
	private UserRepository userRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}" , method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	
    	usuario.setNome(nome);
    	
    	userRepository.save(usuario); // grava no BD
    	
    	return "Olá mundo! " + nome;
    }
    
    @GetMapping(value = "listatodos")
    @ResponseBody //rertorna dados para o corpo da request
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = userRepository.findAll(); //executa consulta no BD   	
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna a lista em json
    }
    
    @PostMapping(value = "salvar") // mapeia a url
    @ResponseBody // descricao da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ //recebe os dados para salvar
    	
    	Usuario user = userRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @DeleteMapping(value = "delete") // mapeia a url
    @ResponseBody // descricao da resposta
    public ResponseEntity<String> delete(@RequestBody @RequestParam Long iduser){ //recebe os dados para deletar
    	
    	userRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso!", HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid") // mapeia a url
    @ResponseBody // descricao da resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){ //recebe os dados para consultar
   
    	Usuario usuario = userRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar") // mapeia a url
    @ResponseBody // descricao da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ //recebe os dados para salvar
    	
    	
    	
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("ID não encontrado!", HttpStatus.BAD_REQUEST);
    		}
    	Usuario user = userRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarpornome") // mapeia a url
    @ResponseBody // descricao da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){ //recebe os dados para consultar
   
    	List<Usuario> usuario = userRepository.buscarPorNome(name.trim().toUpperCase()); //tira espaço e ignore case
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
}
