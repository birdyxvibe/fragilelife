package com.birdy.fragileLife.books;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class FragileLifeBook {

    public static void giveBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta == null) return;

        meta.setTitle("FragileLife");
        meta.setAuthor("Admin");

        List<Component> pages = List.of(
                // Page 1
                Component.text("GAME STRUCTURE", NamedTextColor.GOLD, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("You get 3 Lives:", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("\n- ", NamedTextColor.BLACK))
                        .append(Component.text("Life 1", NamedTextColor.GREEN))
                        .append(Component.text("\n- ", NamedTextColor.BLACK))
                        .append(Component.text("Life 2", NamedTextColor.YELLOW))
                        .append(Component.text("\n- ",NamedTextColor.BLACK))
                        .append(Component.text("Life 3", NamedTextColor.RED))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("You get ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("30❤ ").color(NamedTextColor.RED))
                        .append(Component.text("per life and heart loss is ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("PERMANENT", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("World Size: 500x500", NamedTextColor.BLACK, TextDecoration.BOLD)),

                // Page 2
                Component.text("PVP & TEAMING", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Use caution as ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("PVP ", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text("is ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("ENABLED ", NamedTextColor.GREEN, TextDecoration.BOLD))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Teaming is ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("ALLOWED",  NamedTextColor.GREEN, TextDecoration.BOLD))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("You may ",NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("ONLY ", NamedTextColor.DARK_RED, TextDecoration.BOLD))
                        .append(Component.text("kill other players on your ",NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("THIRD LIFE!", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Keep inventory is ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("ENABLED",  NamedTextColor.GREEN, TextDecoration.BOLD)),
                // Page 3
                Component.text("MISSIONS & STORE", NamedTextColor.DARK_BLUE, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("MISSIONS", NamedTextColor.BLUE, TextDecoration.BOLD))
                        .append(Component.text("\n- /missions", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n- Complete missions to earn ", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("SOUL FRAGMENTS ", NamedTextColor.GOLD, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("STORE", NamedTextColor.YELLOW, TextDecoration.BOLD))
                        .append(Component.text("\n- /store", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n- Spend ", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("SF ", NamedTextColor.GOLD, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("to buy ", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("UNIQUE ITEMS", NamedTextColor.LIGHT_PURPLE, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false)),

                // Page 4
                Component.text("COSMETICS", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Chat Prefix", TextColor.fromHexString("#71A7E9"), TextDecoration.BOLD))
                        .append(Component.text("\n- /tags", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Chat Color",TextColor.fromHexString("#F273C2"), TextDecoration.BOLD))
                        .append(Component.text("\n- /chat", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Join Message",TextColor.fromHexString("#E2BA2B"), TextDecoration.BOLD))
                        .append(Component.text("\n- /greetings", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Nickname",TextColor.fromHexString("#8361EA"), TextDecoration.BOLD))
                        .append(Component.text("\n- /nick", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false)),

                // Page 5
                Component.text("GIFTING", NamedTextColor.DARK_AQUA, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("You can gift other players ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("ONE HEART ", NamedTextColor.RED, TextDecoration.BOLD))
                        .append(Component.text("every ", NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("8HR", TextColor.fromHexString("#E2D043")))
                        .append(Component.text("\n\nThis will not use your hearts.", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false)),

                // Page 6
                Component.text("FUN STUFF",NamedTextColor.AQUA, TextDecoration.BOLD)
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Chat Reactions!",NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("\n- /reaction", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Gamble Diamonds!",NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("\n- /gamble ", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Voice Chat Mod",NamedTextColor.BLACK, TextDecoration.BOLD))
                        .append(Component.text("\n- Please download Simple Voice Chat Mod ", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false)),


                // Page 7
                Component.text("RULES",TextColor.fromHexString("#4D70DB"), TextDecoration.BOLD)
                        .append(Component.text("\n\n1. No destroying the server to an unrepairable degree", NamedTextColor.BLACK).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n\n2. No Cheating, this includes:", NamedTextColor.BLACK).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n - Hacks", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n - X-Ray", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n - Freecam", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n - etc.", NamedTextColor.BLACK, TextDecoration.ITALIC).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n\ncont. on next page", NamedTextColor.BLACK).decoration(TextDecoration.BOLD, true)),

                // Page 8
                Component.text("RULES",TextColor.fromHexString("#4D70DB"), TextDecoration.BOLD)
                        .append(Component.text("\n\n3. Trap kills are allowed on any life", NamedTextColor.BLACK).decoration(TextDecoration.BOLD, false))
                        .append(Component.text("\n\n4. Bug abuse or failure to report will result in a permanent ban", NamedTextColor.BLACK).decoration(TextDecoration.BOLD, false))
                        .append(Component.newline().append(Component.newline()))
                        .append(Component.text("Please report any bugs IMMEDIATELY.",NamedTextColor.BLACK, TextDecoration.BOLD))

        );

        meta.pages(pages);
        book.setItemMeta(meta);
        player.getInventory().addItem(book);
    }
}

