<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>


<style>
.dataTables_paginate{
    float: none !important;
    text-align: center !important;
}
button {
    margin: 10px;
    float: right;
    overflow: hidden;
}

</style>

<body>
    <button onclick="location.href='/post/write'">글쓰기</button>
    <!-- <table id="postList"></table> -->
    <table class="table table-bordered" id="postList" width="100%" cellspacing="0"></table>
</body>

</html>