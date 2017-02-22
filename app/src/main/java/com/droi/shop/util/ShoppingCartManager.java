package com.droi.shop.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.droi.shop.model.CartItem;
import com.google.gson.Gson;

import com.droi.shop.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 2017/2/13.
 */

public class ShoppingCartManager {

    private static final String CART = "cart";
    private static final String KEY = "items";
    static ShoppingCartManager shoppingCartManager;
    private SharedPreferences sp = null;
    private SharedPreferences.Editor edit = null;
    private static Gson GSON = new Gson();

    @SuppressLint("CommitPrefEdits")
    private ShoppingCartManager(Context context) {
        this.sp = context.getSharedPreferences(CART, Context.MODE_PRIVATE);
        edit = sp.edit();
    }

    public static ShoppingCartManager getInstance(Context context) {
        if (shoppingCartManager == null) {
            shoppingCartManager = new ShoppingCartManager(context);
        }

        return shoppingCartManager;
    }

    private void putValue(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("key is empty or null");
        }

        edit.putString(key, GSON.toJson(object));
        edit.apply();
    }

    private <T> T getValue(String key, Class<T> a) {
        String gson = sp.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key "
                        + key + " is instanceof other class");
            }
        }
    }


    public List<CartItem> getList() {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return new ArrayList<>();
        }
        return cartList.getList();
    }

    public String getOrderJson() {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return null;
        }
        List<CartItem> list = cartList.getList();
        List<CartItem> list2 = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return null;
        }
        for (CartItem item : list) {
            if (item.checked) {
                list2.add(item);
            }
        }
        if (list2.size() == 0) {
            return null;
        }
        CartList cartList2 = new CartList();
        cartList2.setList(list2);

        return GSON.toJson(cartList2);
    }

    public List<CartItem> getOrderList(String gson) {
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, CartList.class).getList();
            } catch (Exception e) {
                Log.i("chenpei", "e:" + e.toString());
                return null;
            }
        }
    }


    public void addToCart(Item item) {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            List<CartItem> list = new ArrayList<>();
            cartList = new CartList();
            cartList.setList(list);
        }
        boolean isExist = false;
        List<CartItem> items = cartList.getList();
        for (CartItem cartItem : items) {
            if (item.getObjectId().equals(cartItem.id)) {
                isExist = true;
                cartItem.num += 1;
            }
        }

        if (!isExist) {
            items.add(new CartItem(item.getObjectId(), item, 1));
        }
        putValue(KEY, cartList);
    }

    public void removeFromCart(String id, boolean isAll) {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return;
        }
        List<CartItem> items = cartList.getList();
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
        putValue(KEY, cartList);
    }

    public void setNum(String id, int num) {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return;
        }
        List<CartItem> items = cartList.getList();
        for (CartItem cartItem : items) {
            if (id.equals(cartItem.id)) {
                cartItem.num = num;
            }
        }
        putValue(KEY, cartList);
    }

    public void setChecked(String id, boolean checked) {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return;
        }
        List<CartItem> items = cartList.getList();
        for (CartItem cartItem : items) {
            if (id.equals(cartItem.id)) {
                cartItem.checked = checked;
            }
        }
        putValue(KEY, cartList);
    }

    public boolean isAllChecked() {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return false;
        }
        List<CartItem> items = cartList.getList();
        if (items.size() == 0) {
            return false;
        }
        for (CartItem cartItem : items) {
            if (!cartItem.checked) {
                return false;
            }
        }
        return true;
    }

    public void checkAll(boolean checked) {
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return;
        }
        List<CartItem> items = cartList.getList();
        for (CartItem cartItem : items) {
            cartItem.checked = checked;
        }
        putValue(KEY, cartList);
    }

    public float computeSum() {
        float sum = 0.0f;
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return sum;
        }
        List<CartItem> items = cartList.getList();

        for (CartItem cartItem : items) {
            if (cartItem.checked) {
                sum += cartItem.num * cartItem.item.getPrice();
            }
        }
        return sum;
    }

    public int getCheckNum() {
        int count = 0;
        CartList cartList = getValue(KEY, CartList.class);
        if (cartList == null) {
            return count;
        }
        List<CartItem> items = cartList.getList();

        for (CartItem cartItem : items) {
            if (cartItem.checked) {
                count += cartItem.num;
            }
        }
        return count;
    }

    public void clear() {
        edit.clear();
        edit.commit();
    }

    private class CartList {
        private List<CartItem> list;

        public List<CartItem> getList() {
            return list;
        }

        public void setList(List<CartItem> list) {
            this.list = list;
        }
    }
}
