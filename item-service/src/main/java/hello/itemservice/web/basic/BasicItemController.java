package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //상품 목록
    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "basic/items";
    }

    //상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "basic/item";
    }

    //동일한 URL이지만 Http메소드로 구분
    //상품 등록
    @GetMapping("/add")
    public String add(){
        return "basic/addForm";
    }

    //새로고침 시 마지막에 전송한 데이터를 재전송 >> 중복 저장 발생[Post Redirect Get]
    //상세화면으로 redirect 해주기
    @PostMapping("/add")
    public String save(@ModelAttribute Item item, Model model){
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    //상품 수정
    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item, Model model){
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    
    //테스트용
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

    }
}
