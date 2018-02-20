<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : myXslt.xsl
    Created on : February 16, 2018, 12:58 PM
    Author     : toshiba
    Description:
        Purpose of transformation follows.
-->
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
      <html>
          <head>
            <link rel="stylesheet" type="text/css" href="Style.css"/>
          </head>
        <body>
            <h2>Ruby Messenger</h2> 
            <ul class="chat"> 
                <xsl:apply-templates/>  
            </ul>
        </body>
      </html>
    </xsl:template>

    <xsl:template match="message">
        <xsl:if test="user = sender">
            <li class="chat__bubble chat__bubble--sent"><xsl:apply-templates select="content"/></li>
        </xsl:if>
        <xsl:if test="user != sender">
            <li class="chat__bubble chat__bubble--rcvd"><xsl:apply-templates select="content"/></li>
        </xsl:if>
    </xsl:template>

    <xsl:template match="content">
      <!--Message: <span style="color:#ff0000">-->
      <span>
        <xsl:attribute name="style">
            <xsl:text>color:</xsl:text>
            <xsl:value-of select="../color" />
            <xsl:text>;</xsl:text>
            
            <xsl:text>font-size:</xsl:text>
            <xsl:value-of select="../fontSize" />
            <xsl:text>px;</xsl:text>
            
            <xsl:text>font-weight:</xsl:text>
            <xsl:value-of select="../fontWeight" />
            <xsl:text>;</xsl:text>
            
            <xsl:text>font-style:</xsl:text>
            <xsl:value-of select="../fontStyle" />
            <xsl:text>;</xsl:text>
        </xsl:attribute>
        <xsl:value-of select="."/>
      </span>
      <!--</span>-->
    </xsl:template>

    <!--<xsl:template match="artist">
      Artist: <span style="color:#00ff00">
      <xsl:value-of select="."/></span>
      <br />
    </xsl:template>-->

</xsl:stylesheet>


