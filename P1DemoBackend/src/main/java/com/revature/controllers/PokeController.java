package com.revature.controllers;

import com.revature.models.DTOs.IncomingPokeDTO;
import com.revature.models.Pokemon;
import com.revature.services.PokemonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = "http://localhost:3000")
public class PokeController {

    private PokemonService pokemonService;

    @Autowired
    public PokeController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    //post mapping for inserting new pokemon
    @PostMapping()
    public ResponseEntity<String> addPokemon(@RequestBody IncomingPokeDTO pokeDTO, HttpSession session){

        //If the user is not logged in (if the userId is null), send back a 401
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to catch Pokemon!");
        }

        //Now that we have user info saved (in our HTTP Session), we can attach the stored user Id to the pokeDTO
        pokeDTO.setUserId((int) session.getAttribute("userId"));
        //why do we need to cast to an int? getAttribute returns an Object

        //TODO: try/catch once we decide to do some error handling
        Pokemon p = pokemonService.addPokemon(pokeDTO);

        return ResponseEntity.status(201).body(
                p.getUser().getUsername() + " caught " + p.getName());

    }

}
