/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//config.shiftEnterMode = CKEDITOR.ENTER_P;
    //config.pasteFromWordRemoveStyles = true;  
    // 去掉ckeditor“保存”按钮  
    //config.removePlugins = 'save'; 
	
	
	
	//上传图片调用的action
	//config.filebrowserImageUploadUrl = "bp.forum.ForumTopicAction$upload.json";  

	//去掉底部的提示标签
	config.removePlugins='elementspath';


    //去掉没用的图标
	config.toolbar_Full = [
	//['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
	//['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	//'/',
	//['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
	//['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
	//['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	//['Link','Unlink','Anchor'],
	//['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
	
	//'/',
	['Bold','Italic','Underline'],
	['Font','FontSize'],
	['TextColor','Image'],
	];

	//去掉图像里的'超链接'和'高级'
	CKEDITOR.on('dialogDefinition',function (ev) {
		  var dialogName = ev.data.name;
		  var dialogDefinition = ev.data.definition;
		  if (dialogName == 'image') {
			  dialogDefinition.removeContents('advanced');//消除advanced标签
			  dialogDefinition.removeContents('Link');//消除Link标签
			  dialogDefinition.removeContents('Upload');//消除upload标签
		  }
		 });
	config.fontSize_sizes ='小/10px;中/12px;大/16px';
	
    config.colorButton_colors = '000,F00,00F,008000';

    config.colorButton_enableMore = false;
    
    config.theme = 'default';
    
    config.font_names = 'Arial;宋体';
    
    config.skin = 'kama';
    
    config.uiColor = '#F8F8FF';
};
