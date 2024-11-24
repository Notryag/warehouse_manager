package com.pn.controller;

import com.pn.dto.Unit;
import com.pn.entity.*;
import com.pn.service.*;
import com.pn.utils.Page;
import com.pn.utils.CurrentUser;

import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.pn.utils.TokenUtils;

import java.util.List;
import java.io.File;

@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplyService supplyService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private UnitService unitService;

    @Value("${file.upload-path}")
    private String uploadPath;

    @Autowired
    private TokenUtils tokenUtils;

    public ProductController(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping("/store-list")
    public Result storeList() {
        List<Store> storeList = storeService.queryAllStore();
        return Result.ok(storeList);
    }

    @GetMapping("/brand-list")
    public Result brandList() {
        List<Brand> brands = brandService.queryAllBrand();
        return Result.ok(brands);
    }

    @GetMapping("/category-tree")
    public Result categoryTree() {
        List<ProductType> productTypes = productService.allProductTypeTree();
        return Result.ok(productTypes);
    }

    @GetMapping("/supply-list")
    public Result supplyList() {
        List<Supply> supplies = supplyService.queryAllSupply();
        return Result.ok(supplies);
    }

    @GetMapping("/place-list")
    public Result PlaceList() {
        List<Place> places = placeService.queryAllPlace();
        return Result.ok(places);
    }

    @GetMapping("/unit-list")
    public Result unitList() {
        List<Unit> units = unitService.queryAllUnit();
        return Result.ok(units);
    }

    @GetMapping("/product-page-list")
    public Result productPageList(Page page, Product product) {
        return Result.ok(productService.queryProductPage(page, product));
    }

    @CrossOrigin
    @PostMapping("/img-upload")
    public Result uploadImage(MultipartFile file) {
        try {
            File uploadDirFile = ResourceUtils.getFile(uploadPath);

            String uploadDirPath = uploadDirFile.getAbsolutePath();

            String fileUploadPath = uploadDirPath + "\\" + file.getOriginalFilename();

            file.transferTo(new File(fileUploadPath));
            return Result.ok("upload success");
        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");

        }
    }

    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即添加商品的用户id
        int createBy = currentUser.getUserId();
        product.setCreateBy(createBy);

        //执行业务
        Result result = productService.saveProduct(product);

        //响应
        return result;
    }

    /**
     * 修改商品上下架状态的url接口/product/state-change
     *
     * @RequestBody Product product用于接收并封装请求json数据;
     */
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product) {
        //执行业务
        Result result = productService.updateProductState(product);
        //响应
        return result;
    }

    /**
     * 删除商品的url接口/product/product-delete/{productId}
     */
    @RequestMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId) {
        //执行业务
        Result result = productService.deleteProduct(productId);
        //响应
        return result;
    }


    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改商品的用户id
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);

        //执行业务
        Result result = productService.updateProduct(product);

        //响应
        return result;
    }

}
