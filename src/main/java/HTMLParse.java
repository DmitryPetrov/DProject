package com;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParse
{
	String data;
	HTMLParse (String data)
	{
		this.data = data;
	}

	public Map<String, Map<String, String>> parse() throws IOException
	{
		Document doc = Jsoup.parse(data);
		
		Map<String, Map<String, String>> paper = new LinkedHashMap<>();;
		
		Elements e1 = doc.body().children();
		Iterator<Element> itr1 = e1.iterator();
		while(itr1.hasNext() )
		{
			Element t = itr1.next();
			if(t.tagName().equals("div") )
			{
				if (t.hasAttr("class") )
				{
					if (t.attr("class").equals("paper") )
					{
						Elements e2 = t.children();
						String title = null;
						String names = null;
						Map<String, String> name_ref = null;
						
						Iterator<Element> itr2 = e2.iterator();
						while (itr2.hasNext())
						{
							t = itr2.next();
							if(t.tagName().equals("span") )
							{
								if (t.hasAttr("class"))
								{
									if(t.attr("class").equals("authors"))
									{
										names = t.text();
										name_ref = new LinkedHashMap<String, String>();
										
										Elements e3 = t.children();
										Iterator<Element> itr3 = e3.iterator();
										
										while(itr3.hasNext())
										{
											t = itr3.next();

											if (t.tagName().equals("span"))
											{
												Elements e4 = t.children();
												Iterator<Element> itr4 = e4.iterator();
												
												while(itr4.hasNext())
												{
													t = itr4.next();

													if(t.tagName().equals("a"))
													{
														if (t.hasAttr("href") )
														{
															name_ref.put(t.text(), t.attr("href"));
															names = names.replaceAll(t.text(), "");
														}
													}		
												}	
											} else if(t.tagName().equals("a"))
											{
												if (t.hasAttr("href") )
												{
													name_ref.put(t.text(), t.attr("href"));
													names = names.replaceAll(t.text(), "");
												} else {}
											}
										}
										
										names = names.replaceAll("\\.$", "");
										String[] str1 = names.split(", ");
										for (String name: str1)
										{
											if (name.indexOf(" and ") != -1)
											{
												String[] str2 = name.split(" and ");
												for (String s: str2)
												{
													s.trim();
													if (!s.equals(""))
														name_ref.put(s.trim(), "");
												}
											} else if (!name.equals(""))
											{
												name_ref.put(name.trim(), "");
											}
										}
									}	
									if(t.attr("class").equals("title"))
									{
										title = t.text();
									}							
								}
							}
						}
					paper.put(title, name_ref);
					}
				}
			}	
		}
		return paper;
	}
}
