package utility;

import org.testng.Assert;
import org.testng.annotations.Test;


public class IOUtilsTest {

	@Test
	public void shouldEncodeChineseCharacters(){
		final String str = "信用貸款\n信用卡\n定期存款\n專欄文章\n常見問題\n分享 2\n合作專線： (02) 2547-5809\n星期一至五：09:00 － 18:00\n信用貸款\n比較並申請最佳信用貸款方案\n簡單又便利！\n挑選信用貸款方案\n快速上手\n比較超過 20 家銀行信貸*\n在 Money101.com.tw\n我們致力協助您尋找合適的信用貸款\n各銀行信貸方案比較，完全免費\n輕鬆試算出不同金額與利率的每月還款額\n找出較優惠的手續費與利率方案\n* 銀行資料持續更新中\n信用貸款\n尋找最適合您的信用貸款方案。\n信用卡\n尋找最適合您的信用卡方案。\n定期存款\n尋找最適合您的定期存款方案。\n所有產品\n信用貸款\n選擇 Money101.com.tw 專業比較平台尋找最合適的信用貸款\n了解更多\n快速上手\n信用卡\n選擇 Money101.com.tw 專業比較平台尋找心儀的信用卡\n了解更多\n快速上手\n定期存款\n選擇 Money101.com.tw 專業比較平台尋找最佳定存方案\n了解更多\n快速上手\n"
				+ "簡單 3 步驟\n第一步：\n選擇您的需求\n第二步：\n比較商品特色\n©2014 - 2016 Money101 Limited.台灣最專業且公正的金融產品比較平台\n 理財一零一有限公司版權所有，使用此網站即視為同意我們的 服務條款 與 隱私政策.\n獲取最新消息\nFACEBOOK";
		final String result = IOUtils.toUTF8String(str);
		System.out.println(result);
		Assert.assertEquals(str, result);
	}
	
	@Test
	public void shouldEncondeSpecialCharacters(){
		
		final String str = "https://plus.google.com/+MoneyheroHk";
		final String result = IOUtils.toUTF8String(str);
		System.out.println(result);
		Assert.assertEquals(str, result);
		
	}
	
	@Test
	public void shouldEncodeChineseURL(){
		
		final String str = "http://beta-staging.money101.com.tw/信用貸款/一般貸款";
		final String result = IOUtils.toUTF8String(str);
		System.out.println(result);
		Assert.assertEquals(str, result);
	}
}
