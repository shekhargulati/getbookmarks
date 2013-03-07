// app.js

(function($){

		var Bookmarks = {};
		window.Bookmarks = Bookmarks;
		
		var template = function(name) {
			return Mustache.compile($('#'+name+'-template').html());
		};
		
		Bookmarks.Stories = Backbone.Collection.extend({
//			localStorage : new Store('bookmarks')
			url : 'rest/bookmarks'
		});
		
		Bookmarks.StoryView = Backbone.View.extend({
			template : template('story'),
			events : {
				'click button' : 'removeStory'
			},
			render : function(){
				this.$el.html(this.template(this));
				return this;
			},
			removeStory : function(){
				this.model.destroy();
				return false;
			},
			
			title : function(){
				return this.model.get('title');
			},
			url : function(){
				return this.model.get('url');
			},
			tags : function(){
				return this.model.get('tags');
			}
			
		});
		
		Bookmarks.Index = Backbone.View.extend({
			initialize : function(){
				this.stories = new Bookmarks.Stories();
				this.stories.on('all',this.render,this);
				this.stories.fetch();
			},
		
			render : function(){
				this.$el.html('');
				var form = new Bookmarks.FormView({collection : this.stories});
				this.$el.append(form.render().el);
				this.stories.each(this.renderStory,this);
				return this;
			},
			
			renderStory : function(story){
				var storyView = new Bookmarks.StoryView({model : story});
				this.$el.append(storyView.render().el);
			}

		
		});
		
		Bookmarks.FormView = Backbone.View.extend({
			template : template('form'),
			events : {
				'submit' :'submitStory'
			},
		
			render : function(){
				this.$el.html(this.template);
				return this;
			},
			
			submitStory : function(event){
				event.preventDefault();
				this.collection.create({
					title : this.$('#title').val(),
					url : this.$('#url').val(),
					tags : this.$('#tags').val().split(',')
				});
			}
		
		});
	
		Bookmarks.Router = Backbone.Router.extend({
			initialize : function(options){
				this.el = options.el;
			},
			routes : {
				"" : "index",
			},
			index : function(){
				var indexView = new Bookmarks.Index();			
				this.el.empty();
				this.el.append(indexView.render().el);
			}
		});
	
		var router = new Bookmarks.Router({el : $('#main')});
		Backbone.history.start();
})(jQuery);