package com.example.mvc2.web.validation.form;

import com.example.mvc2.domain.item.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter
@ToString
public class ItemSaveForm {
    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Range(min = 1, max = 9999)
    private Integer quantity;

    public Item toEntity() {
        return new Item(itemName, price, quantity);
    }
}
