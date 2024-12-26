module ApplicationHelper
  def hidden_when_native
    "hidden" if hotwire_native_app?
  end
end
