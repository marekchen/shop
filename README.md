# 电商app
此文档为一分钟系列视频说明教程

## 用户模块
1. `DroiBaaS`提供了`DroiUser`类来帮助开发者建立用户系统
在这里介绍通过继承的方式使用`DroiUser`，这里我创建了一个类`ShopUser`继承于`DroiUser`,在这个类中添加一些我们需要的属性，比如：avatar。

    ``` java
    public class ShopUser extends DroiUser {
        @DroiExpose
        private DroiFile avatar;

        public DroiFile getAvatar() {
            return avatar;
        }
        public void setAvatar(DroiFile avatar) {
            this.avatar = avatar;
        }
    }
    ```

2. 注册用户

    ``` java
    DroiUser user = new DroiUser();
    user.setUserId(userId);
    user.setPassword(password);
    user.signUpInBackground(new DroiCallback<Boolean>() {
        @Override
        public void result(Boolean aBoolean, DroiError droiError) {
            if (droiError.isOk()) {
                // 注册成功
            } else {
                // 注册失败，可以通过 droiError.getCode 获取错误码
            }
        }
    });
    ```

3. 登录

    ``` java
    DroiUser.loginInBackground(userId, password, ShopUser.class, new DroiCallback<DroiUser>() {
        @Override
        public void result(DroiUser droiUser, DroiError droiError) {
            if (droiError.isOk()) {
                // 登录成功
            } else {
                // 登录失败，可以通过 droiError.getCode 获取错误码
            }
        }
    });
    ```

4. 修改密码

    ``` java
    String oldPassword = "oldPassword";
    String newPassword = "newPassword";
    // 一般从EditText获得
    DroiUser myUser = DroiUser.getCurrentUser();
    if (myUser != null && myUser.isAuthorized() && !myUser.isAnonymous()) {
        myUser.changePasswordInBackground(oldPassword, newPassword, new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean) {
                    // 修改成功
                } else {
                    // 修改失败
                }
            }
        });
    }
    ```

5. 上传头像

    ``` java
    DroiFile headIcon = new DroiFile(new File(path));
    ShopUser user = DroiUser.getCurrentUser(ShopUser.class);
    user.setAvatar(headIcon);
    user.saveInBackground(new DroiCallback<Boolean>() {
        @Override
        public void result(Boolean aBoolean, DroiError droiError) {
            if (aBoolean) {
                // 上传成功
            } else {
                // 上传失败
            }
        }
    });
    ```

6. 显示头像

    ``` java
    ShopUser user = DroiUser.getCurrentUser(ShopUser.class);
    if (user != null && user.isAuthorized() && !user.isAnonymous()) {
        if (user.getAvatar() != null) {
            user.getAvatar().getUriInBackground(new DroiCallback<Uri>() {
                @Override
                public void result(Uri uri, DroiError droiError) {
                    if (droiError.isOk()){
                        // 加载头像
                    }
                }
            });
        }
    }
    ```
## 商品展示模块
商品通过继承`DroiObject`类来创建。

    ``` java
    public class Item extends DroiObject {
        @DroiExpose
        private String name;
        @DroiExpose
        private ArrayList<String> images;
        @DroiExpose
        private float price;
        @DroiExpose
        private String description;
        @DroiExpose
        private int commentCount;
        @DroiExpose
        private int praiseCount;
        @DroiExpose
        private String url;
        @DroiExpose
        private String type;

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public ArrayList<String> getImages() {
            return images;
        }
        public void setImages(ArrayList<String> images) {
            this.images = images;
        }
        public float getPrice() {
            return price;
        }
        public void setPrice(float price) {
            this.price = price;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public int getCommentCount() {
            return commentCount;
        }
        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
        public int getPraiseCount() {
            return praiseCount;
        }
        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
    ```

    查询商品列表:

    ``` java
    DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).query(Item.class).build();
    query.runQueryInBackground(new DroiQueryCallback<Item>() {
        @Override
        public void result(List<Item> list, DroiError droiError) {
            if (droiError.isOk() && list.size() > 0) {
                mItems.clear();
                mItems.addAll(list);
                adapter.notifyDataSetChanged();// 通知adapter更新view
            } else {
                // 失败或没有新数据
            }
        }
    });
    ```

## 地址管理模块
每条地址通过继承`DroiObject`类来创建。由于地址和用户相关需要添加一个`userObjectId`字段来标识属于哪个用户。

    ``` java
    public class Address extends DroiObject {
        @DroiExpose
        private String userObjectId;
        @DroiExpose
        private String name;
        @DroiExpose
        private String phoneNum;
        @DroiExpose
        private String location;
        @DroiExpose
        private String address;

        public Address(){
        }
        public Address(String userObjectId, String name, String phoneNum, String location, String address) {
            this.userObjectId = userObjectId;
            this.name = name;
            this.phoneNum = phoneNum;
            this.location = location;
            this.address = address;
        }

        public String getUserObjectId() {
            return userObjectId;
        }
        public void setUserObjectId(String userObjectId) {
            this.userObjectId = userObjectId;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhoneNum() {
            return phoneNum;
        }
        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
    }
    ```

保存地址

    ``` java
        Address address = new Address(userId, name, phone, location, addressText);// 从EditText获取
        address.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean) {
                    // 保存成功
                } else {
                    // 保存失败
                }
            }
        });
    ```

获取地址列表

    ``` java
    DroiCondition cond = DroiCondition.cond("userObjectId", DroiCondition.Type.EQ, DroiUser.getCurrentUser().getObjectId());
    DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).orderBy("_ModifiedTime", false).offset(0).query(Address.class).build();
    query.runQueryInBackground(new DroiQueryCallback<Address>() {
        @Override
        public void result(List<Address> list, DroiError droiError) {
            if (droiError.isOk()) {
                if (list.size() > 0) {
                    mAddressList.clear();
                    mAddressList.addAll(list);
                    mAdapter.notifyDataSetChanged();// 通知adapter更新view
                }
            } else {
                // 做请求失败处理
            }
        }
    });
    ```

## 购物车模块
放到购物车里的商品需要多出几个字段，`id`用于管理，`num`标识该商品的数量，`checked`标识是否被选中（该标志只需本地使用，无需上传云端）

    ``` java
    public class CartItem extends DroiObject {
        @DroiExpose
        private String id;
        @DroiReference
        private Item item;
        @DroiExpose
        private int num;
        private boolean checked = false;

        public CartItem() {
        }
        public CartItem(String mId, Item mItem, int mNum) {
            this.id = mId;
            this.item = mItem;
            this.num = mNum;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public Item getItem() {
            return item;
        }
        public void setItem(Item item) {
            this.item = item;
        }
        public int getNum() {
            return num;
        }
        public void setNum(int num) {
            this.num = num;
        }
        public boolean isChecked() {
            return checked;
        }
        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
    ```

## 订单模块
每条订单通过继承`DroiObject`类来创建。由于订单和用户相关需要添加一个`userObjectId`字段来标识属于哪个用户。

    ``` java
    public class Order extends DroiObject {
        @DroiExpose
        private String userObjectId;
        @DroiExpose
        private ArrayList<DroiReferenceObject> cartItems;
        @DroiReference
        private Address address;
        @DroiExpose
        private int payType;
        @DroiExpose
        private String remark;
        @DroiExpose
        private int receiptType;
        @DroiExpose
        private int state;

        public int getState() {
            return state;
        }
        public void setState(int state) {
            this.state = state;
        }
        public ArrayList<CartItem> getCartItems() {
            ArrayList<CartItem> lists = new ArrayList<>();
            if (cartItems != null) {
                for (DroiReferenceObject ref : cartItems) {
                    lists.add((CartItem) ref.droiObject());
                }
            }
            return lists;
        }
        public void setCartItems(List<CartItem> cartItems) {
            ArrayList<DroiReferenceObject> castList = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                DroiReferenceObject ref = new DroiReferenceObject();
                ref.setDroiObject(cartItem);
                castList.add(ref);
            }
            this.cartItems = castList;
        }
        public String getUserObjectId() {
            return userObjectId;
        }
        public void setUserObjectId(String userObjectId) {
            this.userObjectId = userObjectId;
        }
        public Address getAddress() {
            return address;
        }
        public void setAddress(Address address) {
            this.address = address;
        }
        public int getPayType() {
            return payType;
        }
        public void setPayType(int payType) {
            this.payType = payType;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public int getReceiptType() {
            return receiptType;
        }
        public void setReceiptType(int receiptType) {
            this.receiptType = receiptType;
        }
    }
    ```

提交订单

    ``` java
    Order order = new Order();
    order.setUserObjectId(DroiUser.getCurrentUser().getObjectId());
    order.setAddress(address);
    order.setCartItems(cartItems);
    order.setPayType(1);
    order.setReceiptType(1);
    order.setRemark(remarkTextView.getText().toString());
    order.saveInBackground(new DroiCallback<Boolean>() {
        @Override
        public void result(Boolean aBoolean, DroiError droiError) {
            if (aBoolean) {
                // 成功
            } else {
                // 失败
            }
        }
    });
    ```

# License
```
Copyright 2017 Droi, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```