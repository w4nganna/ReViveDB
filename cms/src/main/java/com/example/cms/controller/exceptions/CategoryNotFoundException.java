package com.example.cms.controller.exceptions;

public class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException(Long categoryId) {
    super("Could not find category" + categoryId);
  }
}
