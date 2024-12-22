Rails.application.routes.draw do
  get "pages/home"
  resources :urls

  get "up" => "rails/health#show", as: :rails_health_check

  root "pages#home"
end
