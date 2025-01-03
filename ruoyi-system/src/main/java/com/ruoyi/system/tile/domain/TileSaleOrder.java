package com.ruoyi.system.tile.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单对象 tile_sale_order
 * 
 * @author ruoyi
 */
public class TileSaleOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 订单日期 */
    @Excel(name = "订单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private String orderDate;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal totalAmount;

    /** 订单状态（1待审核 2已审核 3已出库 4已完成 5已取消） */
    @Excel(name = "订单状态", readConverterExp = "1=待审核,2=已审核,3=已出库,4=已完成,5=已取消")
    private String orderStatus;

    /** 支付状态（1未支付 2部分支付 3已支付） */
    @Excel(name = "支付状态", readConverterExp = "1=未支付,2=部分支付,3=已支付")
    private String paymentStatus;

    /** 已支付金额 */
    @Excel(name = "已支付金额")
    private BigDecimal paymentAmount;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String deliveryAddress;

    /** 收货电话 */
    @Excel(name = "收货电话")
    private String deliveryPhone;

    /** 收货联系人 */
    @Excel(name = "收货联系人")
    private String deliveryContact;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;

    /** 销售订单明细列表 */
    private List<TileSaleOrderDetail> details;

    /** 客户信息 */
    private TileCustomer customer;

    /** 订单状态常量 */
    public static final String STATUS_PENDING = "0";    // 待审核
    public static final String STATUS_APPROVED = "1";   // 已审核
    public static final String STATUS_SHIPPED = "2";    // 已出库
    public static final String STATUS_COMPLETED = "3";  // 已完成
    public static final String STATUS_CANCELLED = "4";  // 已取消

    /** 支付状态常量 */
    public static final String PAYMENT_UNPAID = "0";     // 未支付
    public static final String PAYMENT_PARTIAL = "1";    // 部分支付
    public static final String PAYMENT_COMPLETED = "2";  // 已支付

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public void setOrderDate(String orderDate) 
    {
        this.orderDate = orderDate;
    }

    public String getOrderDate() 
    {
        return orderDate;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }

    public void setPaymentStatus(String paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() 
    {
        return paymentStatus;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
    }

    public void setDeliveryAddress(String deliveryAddress) 
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() 
    {
        return deliveryAddress;
    }

    public void setDeliveryPhone(String deliveryPhone) 
    {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryPhone() 
    {
        return deliveryPhone;
    }

    public void setDeliveryContact(String deliveryContact) 
    {
        this.deliveryContact = deliveryContact;
    }

    public String getDeliveryContact() 
    {
        return deliveryContact;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public String getAuditBy() 
    {
        return auditBy;
    }

    public void setAuditBy(String auditBy) 
    {
        this.auditBy = auditBy;
    }

    public List<TileSaleOrderDetail> getDetails() 
    {
        return details;
    }

    public void setDetails(List<TileSaleOrderDetail> details) 
    {
        this.details = details;
    }

    public TileCustomer getCustomer() 
    {
        return customer;
    }

    public void setCustomer(TileCustomer customer) 
    {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("customerId", getCustomerId())
            .append("orderDate", getOrderDate())
            .append("totalAmount", getTotalAmount())
            .append("orderStatus", getOrderStatus())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentAmount", getPaymentAmount())
            .append("deliveryAddress", getDeliveryAddress())
            .append("deliveryPhone", getDeliveryPhone())
            .append("deliveryContact", getDeliveryContact())
            .append("auditTime", getAuditTime())
            .append("auditBy", getAuditBy())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
