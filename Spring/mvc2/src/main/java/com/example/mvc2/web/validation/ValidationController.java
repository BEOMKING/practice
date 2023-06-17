package com.example.mvc2.web.validation;

import com.example.mvc2.domain.item.DeliveryCode;
import com.example.mvc2.domain.item.Item;
import com.example.mvc2.domain.item.ItemRepository;
import com.example.mvc2.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/items")
@RequiredArgsConstructor
public class ValidationController {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(final WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

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
        return "validation/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/item";
    }

    @GetMapping("/add")
    public String addForm(final Model model) {
        model.addAttribute("item", new Item());
        return "validation/addForm";
    }

//    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
//        }
//
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            final int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
//            }
//        }
//
//        if (bindingResult.hasErrors()) {
//            log.error("errors={}", bindingResult);
//            return "validation/addForm";
//        }
//
//        final Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
//        }
//
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            final int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
//            }
//        }
//
//        if (bindingResult.hasErrors()) {
//            log.error("errors={}", bindingResult);
//            return "validation/addForm";
//        }
//
//        final Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
////        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
//        if (!StringUtils.hasText(item.getItemName())) {
//             bindingResult.rejectValue("itemName", "required");
//        }
//
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
//        }
//
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
//        }
//
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            final int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }
//
//        if (bindingResult.hasErrors()) {
//            log.error("errors={}", bindingResult);
//            return "validation/addForm";
//        }
//
//        final Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV4(@ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        itemValidator.validate(item, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            log.error("errors={}", bindingResult);
//            return "validation/addForm";
//        }
//
//        final Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV5(@Validated @ModelAttribute Item item, final BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "validation/addForm";
        }

        final Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/items/{itemId}";
    }

}

