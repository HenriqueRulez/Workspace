package br.senai.sp.informatica.todolist.controller;

import java.net.URI;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;

import br.senai.sp.informatica.todolist.dao.UsuarioDao;
import br.senai.sp.informatica.todolist.modelo.Usuario;

@RestController
public class UsuarioRestController {
	
	//CHAVE PARA ENCRYPT E DECRYPT
	public static final String SECRET = "todolist";
	public static final String ISSUER = "Apenas o nome do emissor";

	@Autowired
	private UsuarioDao usuarioDao;
	
	
	@RequestMapping(value="/usuario", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Usuario> inserir(@RequestBody Usuario usuario){
		
		try {
			
			usuarioDao.cadastrar(usuario);
			URI location = new URI("/usuario/"+usuario.getId());			
			
			return ResponseEntity.created(location).body(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//Token de autenticação
	@RequestMapping(value="/login", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> logar(@RequestBody Usuario usuario){
		
		//Tentar logar, caso contrário, retorna mensagem de não autorizado.
		try {
			
			usuario = usuarioDao.logar(usuario);
			if(usuario != null){
				//Data de emissão do token
				//Issued At (iat)
				long iat = System.currentTimeMillis() / 1000;
				//Data de expiração do token
				long exp = iat + 60;
				
				// JWT = JSON Web Token (emissor, data de emissão, data de expiração)
				//Objeto que ira gerar o token
				JWTSigner signer = new JWTSigner(SECRET);
				
				HashMap<String, Object> claims = new HashMap<>();
				claims.put("iat", iat);
				claims.put("exp", exp);
				claims.put("iss", ISSUER);
				claims.put("id_usuario", usuario.getId());
				
				//Gerar token
				String jwt = signer.sign(claims);
				
				//Retornar em forma de JSON
				JSONObject token = new JSONObject();
				token.put("token", jwt);
				
				return ResponseEntity.ok(token.toString());
				
			}else{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
}
