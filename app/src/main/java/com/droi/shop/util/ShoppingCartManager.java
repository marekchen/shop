package com.droi.shop.util;

import com.droi.shop.model.Item;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by marek on 2017/2/13.
 */

public class ShoppingCartManager {

    static ConcurrentSkipListSet<CartItem> items = new ConcurrentSkipListSet<>();

    public static ConcurrentSkipListSet<CartItem> getList() {
        return items;
    }

    public static void addToCart(Item item) {
        boolean isExist = false;
        for (CartItem cartItem : items) {
            if (item.getObjectId().equals(cartItem.id)) {
                isExist = true;
                cartItem.num += 1;
            }
        }
        if (!isExist) {
            items.add(new CartItem(item.getObjectId(), item, 1));
        }
    }

    public static void removeFromCart(String id, boolean isAll) {
        for (CartItem item : items) {
            if (item.id.equals(id)) {
                if (isAll) {
                    items.remove(item);
                } else {
                    item.num -= 1;
                    if (item.num == 0) {
                        items.remove(item);
                    }
                }
            }
        }
    }

    public static void setNum(String id, int num) {
        for (CartItem cartItem : items) {
            if (id.equals(cartItem.id)) {
                cartItem.num = num;
            }
        }
    }

    public static void setChecked(String id, boolean checked) {
        for (CartItem cartItem : items) {
            if (id.equals(cartItem.id)) {
                cartItem.checked = checked;
            }
        }
    }

    public static boolean isAllChecked() {
        for (CartItem cartItem : items) {
            if (!cartItem.checked) {
                return false;
            }
        }
        return true;
    }

    public static void checkAll(boolean checked) {
        for (CartItem cartItem : items) {
            cartItem.checked = checked;
        }
    }

    public static float computeSum() {
        float sum = 0.0f;
        for (CartItem cartItem : items) {
            if (cartItem.checked) {
                sum += cartItem.num * cartItem.item.getPrice();
            }
        }
        return sum;
    }

    public static class CartItem implements Comparable<CartItem> {
        public String id;
        public Item item;
        public int num;
        public boolean checked = false;

        CartItem(String mId, Item mItem, int mNum) {
            id = mId;
            this.item = mItem;
            this.num = mNum;
        }

        @Override
        public int compareTo(CartItem o) {
            if (id.equals(o.id)) {
                return 0;
            } else if (id.hashCode() > o.id.hashCode()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
