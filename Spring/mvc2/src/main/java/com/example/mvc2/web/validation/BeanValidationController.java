package com.example.mvc2.web.validation;

import com.example.mvc2.domain.item.DeliveryCode;
import com.example.mvc2.domain.item.Item;
import com.example.mvc2.domain.item.ItemRepository;
import com.example.mvc2.domain.item.ItemType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/bean-validation/items")
@RequiredArgsConstructor
public class BeanValidationController {

    private final ItemRepository itemRepository;
//    private final ItemValidator itemValidator;
//
//    @InitBinder
//    public void init(final WebDataBinder dataBinder) {
//        dataBinder.addValidators(itemValidator);
//    }

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        final Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        final List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "bean-validation/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "bean-validation/item";
    }

    @GetMapping("/add")
    public String addForm(final Model model) {
        model.addAttribute("item", new Item());
        return "bean-validation/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV5(@Validated @ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            log.error("errors={}", bindingResult);
//            return "bean-validation/addForm";
//        }
//
//        final Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/bean-validation/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (item.getPrice() != null && item.getQuantity() != null) {
            final int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, "가격 * 수량의 합은 10,000원 이상이어야 합니다.");
            }
        }

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "bean-validation/addForm";
        }

        final Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/bean-validation/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "bean-validation/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Valid @ModelAttribute Item item, final BindingResult bindingResult) {
        if (item.getPrice() != null && item.getQuantity() != null) {
            final int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, "가격 * 수량의 합은 10,000원 이상이어야 합니다.");
            }
        }

        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "bean-validation/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/bean-validation/items/{itemId}";
    }

}

