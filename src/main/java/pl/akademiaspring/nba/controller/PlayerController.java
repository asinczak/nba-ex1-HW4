package pl.akademiaspring.nba.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import pl.akademiaspring.nba.model.Player;
import java.io.IOException;
import java.util.List;


@Controller
public class PlayerController {

    public List<Player> getPlayersList() {
        RestTemplate restTemplate = new RestTemplate();

        JsonNode table = restTemplate.getForObject("https://www.balldontlie.io/api/v1/players", JsonNode.class).get("data");

        ObjectMapper mapper = new ObjectMapper();
        List<Player> playersList = null;
        try {

            playersList = mapper.readValue(table.toString(), new TypeReference<List<Player>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playersList;
    }

    @GetMapping("/players")
    public String getPlayers(Model model) {
        model.addAttribute("players", getPlayersList());
        return "playersView";
    }
}
