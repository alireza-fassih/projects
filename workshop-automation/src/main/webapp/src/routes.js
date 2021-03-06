import React from 'react';
import Loadable from 'react-loadable'

import DefaultLayout from './containers/DefaultLayout';

function Loading() {
  return <div>Loading...</div>;
}

const UserList = Loadable({
  loader: () => import('./views/User/UserList'),
  loading: Loading,
});

const RawMaterialCategory = Loadable({
  loader: () => import('./views/RawMaterialCategory/RawMaterialCategory'),
  loading: Loading,
});

const GoodsCategory = Loadable({
  loader: () => import('./views/GoodsCategory/GoodsCategory'),
  loading: Loading,
});


const Goods = Loadable({
  loader: () => import('./views/Goods/Goods'),
  loading: Loading,
});

const GoodsRawMaterial = Loadable({
  loader: () => import('./views/GoodsRawMaterial/GoodsRawMaterial'),
  loading: Loading,
});


const RawMaterial = Loadable({
  loader: () => import('./views/RawMaterial/RawMaterial'),
  loading: Loading,
});


const GoodsListForOrder = Loadable({
  loader: () => import('./views/CreateOrder/GoodsListForOrder'),
  loading: Loading,
});

const CreateOrder = Loadable({
  loader: () => import('./views/CreateOrder/CreateOrder'),
  loading: Loading,
});

const MyOrderList = Loadable({
  loader: () => import('./views/MyOrderList/MyOrderList'),
  loading: Loading,
});

const AllOrderList = Loadable({
  loader: () => import('./views/AllOrderList/AllOrderList'),
  loading: Loading,
});

const OrderStateList = Loadable({
  loader: () => import('./views/OrderState/OrderState'),
  loading: Loading,
});

const NotificationList = Loadable({
  loader: () => import('./views/Notification/Notification'),
  loading: Loading,
});

const Dashboard = Loadable({
  loader: () => import('./views/Dashboard/Dashboard'),
  loading: Loading,
});

const Logout = Loadable({
  loader: () => import('./views/Logout/Logout'),
  loading: Loading,
});

const UnverifierList = Loadable({
  loader: () => import('./views/AllOrderList/NewOrdersList'),
  loading: Loading,
});

const EditOrder = Loadable({
  loader: () => import('./views/MyOrderList/EditOrder'),
  loading: Loading,
});

const PriceList = Loadable({
  loader: () => import('./views/PriceList/PriceList'),
  loading: Loading,
});

const PriceListDownload  = Loadable({
  loader: () => import('./views/PriceList/PriceListDl'),
  loading: Loading,
});


const OrderStateReport  = Loadable({
  loader: () => import('./views/Report/OrderStateReport'),
  loading: Loading,
});

const OrderUnitReport  = Loadable({
  loader: () => import('./views/Report/AdvanceReport'),
  loading: Loading,
});

const routes = [
  { path: '/', exact: true, name: '????????', component: DefaultLayout , role: "USER" },
  { path: '/dashboard', name: '??????????????', component: Dashboard , role: "USER" },
  { path: '/logout', name: '????????', component: Logout },
  { path: '/dl-price-list', name: '???????? ????????', component: PriceListDownload },
  { path: '/user/list', name: '???????? ??????????????', component: UserList , role: "ADMIN"},
  { path: '/rawMaterialCategory/list', name: '???????? ?????? ??????????????????', component: RawMaterialCategory, role: "ADMIN"},
  { path: '/rawMaterial/list', name: '???????? ??????????????????', component: RawMaterial, role: "ADMIN"},
  { path: '/goodsCategory/list', name: '???????? ?????????????? ????????', component: GoodsCategory, role: "ADMIN"},
  { path: '/goods/list', name: '???????? ????????', component: Goods, role: "ADMIN"},
  { path: '/goodsRawMaterial/list', name: '???????? ???????? ????????', component: GoodsRawMaterial, role: "ADMIN"},
  { path: '/create-order-list', name: '?????? ??????????????', component: GoodsListForOrder, role: "USER"},
  { path: '/create-order/:id', name: '?????? ??????????????', component: CreateOrder, role: "USER"},
  { path: '/edit-order/:id', name: '???????????? ??????????????', component: EditOrder, role: "USER"},
  { path: '/my-orders', name: '???????? ?????????????? ????', component: MyOrderList, role: "USER"},
  { path: '/unverifier-orders', name: '?????????????? ?????????? ????????', component: UnverifierList, role: "VERIFIER"},
  { path: '/all-orders', name: '???????? ???????? ?????????????? ????', component: AllOrderList, role: "ADMIN"},
  { path: '/orderState', name: '???????? ??????????', component: OrderStateList, role: "ADMIN"},
  { path: '/notifications', name: '???????? ?????????????? ????', component: NotificationList, role: "ADMIN"},
  { path: '/price-list/list', name: '???????? ???????? ????', component: PriceList, role: "ADMIN"},
  { path: '/report-state', name: '?????????? ???????????? ??????????', component: OrderStateReport, role: "ADMIN" },
  { path: '/report-unit', name: '?????????? ?????? ????', component: OrderUnitReport, role: "ADMIN" },


];

export default routes;
